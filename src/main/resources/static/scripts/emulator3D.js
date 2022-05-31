let scene;
let canvas;
let renderer;

function emu3D() {

    //canvas setup
    canvas = document.querySelector('#c');
    renderer = new THREE.WebGLRenderer({antialias: true, canvas});
    renderer.setSize(canvas.innerWidth, canvas.innerHeight);

    //scene setup
    scene = new THREE.Scene();
    scene.background = new THREE.Color('#333');

    //camera and controls setup
    const fov = 45;
    let aspect = canvas.innerWidth / canvas.innerHeight;
    const near = 1;
    const far = 10000;
    let camera = new THREE.PerspectiveCamera(fov, aspect, near, far);
    scene.add(camera);
    const controls = new THREE.OrbitControls(camera, renderer.domElement)
    controls.maxPolarAngle = degToRad(160);
    controls.minPolarAngle = degToRad(20);
    camera.position.set(500, 100, 200);
    controls.update();

    //lighting setup
    const dir = new THREE.DirectionalLight(0xffffff, 1);
    const dirm = new THREE.DirectionalLight(0xff8800, 0.5);
    dirm.translateY(-10);

    const ambient = new THREE.AmbientLight(0x404040, 2.5);
    scene.add(dir);
    scene.add(dirm);
    scene.add(ambient);
    dirm.target = dir;
    //loader setup
    let fbxloader = new THREE.FBXLoader();
    let palm = scene;
    let wrist = new THREE.Object3D()
    const ppos = blenderToThree(new THREE.Vector3(0, 0, 0));

    let frame = 0;
    let path = '';

    fbxloader.load('scripts/simulatorModels/hand_new/new_palm.fbx', async function (object) {

        scene.add(wrist)
        wrist.add(object);
        object.position.x = -ppos.x;
        object.position.y = -ppos.y;
        object.position.z = -ppos.z;
        palm = object;
        palm.name = 'palm';

        await loadFingers();
    });

    let fingers = [
        [palm, palm, palm],
        [palm, palm, palm],
        [palm, palm, palm],
        [palm, palm, palm],
        [palm, palm, palm]
    ];
    /**
     * This array is necessary in order to set proper rotation points for the imported models.
     * The coordinates are copied from the corresponding .blend file.
     * TODO: change this array from being hardcoded to being read from a .json file to enable the use of multiple models without code changes.
     * @type {Vector3[][]}
     */
    const fpos = [ //DO NOT TOUCH!, new version
        [
            blenderToThree(new THREE.Vector3(3.59622, 0.353044, -0.018004)),
            blenderToThree(new THREE.Vector3(2.90712, 0.353044, -0.000326)),
            blenderToThree(new THREE.Vector3(1.98374, 0.355688, -0.005718))
        ], [
            blenderToThree(new THREE.Vector3(3.78517, -0.121099, -0.018709)),
            blenderToThree(new THREE.Vector3(3.02742, -0.121099, 0.000276)),
            blenderToThree(new THREE.Vector3(2.03528, -0.118192, -0.005514))
        ], [
            blenderToThree(new THREE.Vector3(3.55936, -0.61488, -0.018362)),
            blenderToThree(new THREE.Vector3(2.84435, -0.61488, -0.00002)),
            blenderToThree(new THREE.Vector3(1.90818, -0.612137, -0.005614))
        ], [
            blenderToThree(new THREE.Vector3(3.09757, -1.02955, -0.016404)),
            blenderToThree(new THREE.Vector3(2.52417, -1.02955, -0.001695)),
            blenderToThree(new THREE.Vector3(1.77339, -1.02735, -0.006181))
        ], [
            blenderToThree(new THREE.Vector3(1.99089, 1.39046, -0.004993)),
            blenderToThree(new THREE.Vector3(1.38421, 1.16946, -0.004993)),
            blenderToThree(new THREE.Vector3(0.46195, 0.78016, -0.004993))
        ]]
    ;

    /**
     * places a specific finger segment from the /simulatorModels folder
     * @param object loaded object
     * @param d digit
     * @param s segment number
     */
    async function loadSegment(object, d, s) {
        let point = new THREE.Object3D();
        if (s < 3)
            fingers[d - 1][s].add(point);
        else
            palm.add(point);
        point.add(object)
        fingers[d - 1][s - 1] = point;

        point.position.x = fpos[d - 1][s - 1].x;
        point.position.y = fpos[d - 1][s - 1].y;
        point.position.z = fpos[d - 1][s - 1].z;
        if (s < 3) {
            point.position.x -= fpos[d - 1][s].x;
            point.position.y -= fpos[d - 1][s].y;
            point.position.z -= fpos[d - 1][s].z;
        }
    }

    function loadTheFinger(resolve, reject) {
        fbxloader.load(path, object => resolve(object));
    }

    /**
     * loads a finger from the scripts/simulatorModels folder
     */
    async function loadFingers() {
        for (let s = 3; s > 0; s--) {
            for (let d = 1; d < 6; d++) {
                path = 'scripts/simulatorModels/hand_new/new_simHand_id' + d + '' + s + '.fbx';
                let object = await new Promise(loadTheFinger);
                object.name = namePart(d, s);
                await loadSegment(object, d, s);
            }
        }
    }

    /**
     * generates a name for a loaded segment
     * @param d digit
     * @param s segment number
     * @returns {string} new object name
     */
    function namePart(d, s) {
        return 'digit' + d + ' segment' + s;
    }

    /**
     * The almighty render function
     */
    function render() {
        frame++;

        let data;
        try {
            if (frame % 2 === 0) {
                data = apply_filter_to_data(data_buffer.shift());
                frame = 0;
                fingers[4][2].rotation.y = degToRad(-data[0]); // th_rot_orthogonal
                fingers[4][2].rotation.x = degToRad(-data[1]); // th_rot_palm
                fingers[4][1].rotation.y = degToRad(-data[2]); // th_base
                fingers[4][0].rotation.y = degToRad(-data[3]); // th_tip
                fingers[0][2].rotation.y = degToRad(-data[4]); // if_base_rot
                fingers[0][2].rotation.z = degToRad(-data[5]); // if_base
                fingers[0][1].rotation.z = degToRad(-data[6]); // if_middle
                fingers[0][0].rotation.z = degToRad(-data[7]); // if_tip
                fingers[1][2].rotation.y = degToRad(-data[8]); // mf_base_rot
                fingers[1][2].rotation.z = degToRad(-data[9]); // mf_base
                fingers[1][1].rotation.z = degToRad(-data[10]); // mf_middle
                fingers[1][0].rotation.z = degToRad(-data[11]); // mf_tip
                fingers[2][2].rotation.y = degToRad(-data[12]); // rf_base_rot
                fingers[2][2].rotation.z = degToRad(-data[13]); // rf_base
                fingers[2][1].rotation.z = degToRad(-data[14]); // rf_middle
                fingers[2][0].rotation.z = degToRad(-data[15]); // rf_tip
                fingers[3][2].rotation.y = degToRad(-data[16]); // p_base_rot
                fingers[3][2].rotation.z = degToRad(-data[17]); // p_base
                fingers[3][1].rotation.z = degToRad(-data[18]); // p_middle
                fingers[3][0].rotation.z = degToRad(-data[19]); // p_tip
                wrist.rotation.x = degToRad(0);//-data[20]); // wr_lr
                wrist.rotation.z = degToRad(0);//-data[21]); // wr_bf
            }
        } catch (e) {
        }

        camera.aspect = canvas.clientWidth / canvas.clientHeight;
        renderer.setSize(canvas.clientWidth, canvas.clientHeight, false);
        camera.updateProjectionMatrix();

        requestAnimationFrame(render);

        controls.update();
        renderer.render(scene, camera);
    }

    window.requestAnimationFrame(render);
}

/**
 * converts blender coordinates to three.js coordinates
 * @param blenderCoords Coordinates from Blender
 * @returns {Vector3} Coordinates for three.js
 */
function blenderToThree(blenderCoords) {
    let ret = new THREE.Vector3();
    ret.x = blenderCoords.x * 100;
    ret.y = blenderCoords.z * 100;
    ret.z = blenderCoords.y * -100;
    return ret;
}

/**
 * converts rad to degrees
 * @param rad angle in rad
 * @returns {number} angle in degrees
 */
function radToDeg(rad) {
    return rad * 180 / Math.PI;
}

/**
 * converts degrees to rad
 * @param deg angle in degrees
 * @returns {number} angle in rad
 */
function degToRad(deg) {
    return deg / 180 * Math.PI;
}
