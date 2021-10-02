import {OrbitControls} from './lib/three.js-master/examples/jsm/controls/OrbitControls.js';
import {FBXLoader} from "./lib/three.js-master/examples/jsm/loaders/FBXLoader.js";

emu3D();

function emu3D() {

    //canvas setup
    let canvas = document.querySelector('#c');
    const renderer = new THREE.WebGLRenderer({antialias: true, canvas});
    renderer.setSize(canvas.innerWidth, canvas.innerHeight);

    //scene setup
    const scene = new THREE.Scene();
    scene.background = new THREE.Color('#222');

    //camera and controls setup
    const fov = 45;
    let aspect = window.innerWidth / window.innerHeight;
    const near = 1;
    const far = 10000;
    let camera = new THREE.PerspectiveCamera(fov, aspect, near, far);
    scene.add(camera);
    const controls = new OrbitControls(camera, renderer.domElement)
    controls.maxPolarAngle = degToRad(160);
    controls.minPolarAngle = degToRad(20);
    camera.position.set(500, 100, 200);
    controls.update();

    //lighting setup
    const dir = new THREE.DirectionalLight(0xffffff, 1)
    const ambient = new THREE.AmbientLight(0x404040, 2.5);
    scene.add(dir);
    scene.add(ambient);

    //loader setup
    let fbxloader = new FBXLoader();
    let palm = scene;
    let wrist = new THREE.Object3D()
    const ppos = blenderToThree(new THREE.Vector3(-1.25736, 0.039318, -0.020059));

    fbxloader.load('scripts/simulatorModels/palm.fbx', function (object) {

        scene.add(wrist)
        wrist.add(object);
        object.position.x = -ppos.x;
        object.position.y = -ppos.y;
        object.position.z = -ppos.z;
        palm = object;
        palm.name='palm';

        loadFingers();
    });

    let fingers = [[palm, palm, palm], [palm, palm, palm], [palm, palm, palm], [palm, palm, palm], [palm, palm, palm]];
    const fpos = [ //DO NOT TOUCH!
        [
            blenderToThree(new THREE.Vector3(2.38672, 0.434034, 0.00079)),
            blenderToThree(new THREE.Vector3(1.7966, 0.438078, 0.001156)),
            blenderToThree(new THREE.Vector3(1.07962, 0.438278, 0.001571))
        ], [
            blenderToThree(new THREE.Vector3(2.56557, -0.091211, -0.002479)),
            blenderToThree(new THREE.Vector3(1.91216, -0.090908, -0.0021)),
            blenderToThree(new THREE.Vector3(1.07947, -0.091304, -0.001621))
        ], [
            blenderToThree(new THREE.Vector3(2.37357, -0.626907, -0.005598)),
            blenderToThree(new THREE.Vector3(1.73113, -0.61744, -0.00517)),
            blenderToThree(new THREE.Vector3(0.988469, -0.60875, -0.004688))
        ], [
            blenderToThree(new THREE.Vector3(2.07439, -1.08967, -0.008215)),
            blenderToThree(new THREE.Vector3(1.51741, -1.08203, -0.007847)),
            blenderToThree(new THREE.Vector3(0.913147, -1.08187, -0.007497))
        ], [
            blenderToThree(new THREE.Vector3(1.10977, 1.15397, 0.000587)),
            blenderToThree(new THREE.Vector3(0.43031, 1.1529, 0.000975)),
            blenderToThree(new THREE.Vector3(-0.001847, 0.982914, 0.005479))
        ]]
    ;

    /**
     * places a specific finger segment from the /simulatorModels folder
     * @param object loaded object
     * @param d digit
     * @param s segment number
     * @returns {Promise<void>} nothing lol
     */
    async function loadSegment( object, d, s) {
        let point = new THREE.Object3D();
        if(s<3)
            fingers[d - 1][s].add(point);
        else
            palm.add(point);
        point.add(object)
        fingers[d - 1][s-1] = point;

        point.position.x = fpos[d - 1][s-1].x;
        point.position.y = fpos[d - 1][s-1].y;
        point.position.z = fpos[d - 1][s-1].z;
        if(s<3){
            point.position.x -= fpos[d - 1][s].x;
            point.position.y -= fpos[d - 1][s].y;
            point.position.z -= fpos[d - 1][s].z;
        }
        object.material=new THREE.MeshStandardMaterial();

    }

    /**
     * loads a finger from the /simulatorModels folder
     */
    function loadFingers() {
        for (let s = 3; s > 0; s--)
            for (let d = 1; d < 6; d++) {
                fbxloader.load('scripts/simulatorModels/simHand_id' + d + '' + s + '.fbx',  async object => {
                    object.name = namePart(d, s);
                    await loadSegment(object, d, s);
                });
            }
    }

    /**
     * generates a name for a loaded segment
     * @param d digit
     * @param s segment number
     * @returns {string} new object name
     */
    function namePart(d, s) {
        return 'digit' + d + 'segment' + s;
    }
    let raycaster = new THREE.Raycaster();
    let mouse = new THREE.Vector2();

    // window.addEventListener('mousedown',onMouseDown, false);
    canvas.onclick = event => onMouseDown(event);

    let lastsel=0;

    /**
     * Function to be executed when a mouse button is clicked.
     * fires raycast to detect segment and log rotation dat a
     * @param event onMouseDown event
     */
    function onMouseDown(event) {
        // update the picking ray with the camera and mouse position
        mouse.x = (event.clientX / window.innerWidth) * 2 - 1;
        mouse.y = -(event.clientY / window.innerHeight) * 2 + 1;
        raycaster.setFromCamera( mouse, camera );

        // calculate objects intersecting the picking ray
        let intersects = raycaster.intersectObjects( scene.children , true);
        if(lastsel!==0){
            lastsel.material=new THREE.MeshStandardMaterial({color:0xcccccc});
        }
        if(intersects.length>0) {
            console.log(intersects[0].object.parent.name);
            let quat;
            quat=intersects[0].object.parent.getWorldQuaternion(new THREE.Quaternion());
            let rotation = new THREE.Euler().setFromQuaternion(quat);
            intersects[0].object.material=new THREE.MeshStandardMaterial({color: 0xff7800});
            console.log(radToDeg(rotation.z));
            lastsel=intersects[0].object;
        }
        else
            lastsel=0;
    }

    /**
     * Fixes the renderer when the window changes
     * @param renderer the renderer
     * @returns {boolean} if resize is required
     */
    function resizeRendererToDisplaySize(renderer) {
        const canvas = renderer.domElement;
        const width = window.innerWidth;
        const height = window.innerHeight;
        const needResize = canvas.width !== width || canvas.height !== height;
        if (needResize) {
            renderer.setSize(width, height, true);
        }
        return needResize;
    }

    /**
     * The almighty render function
     */
    function render() {

        try {
            dat_json = dat_json_buffer.shift();

            wrist.rotation.x=degToRad(-dat_json.wr_lr);
            wrist.rotation.z=degToRad(-dat_json.wr_bf);
            fingers[0][2].rotation.y=degToRad(-dat_json.if_base_rot);
            fingers[0][2].rotation.z=degToRad(-dat_json.if_base);
            fingers[0][1].rotation.z=degToRad(-dat_json.if_middle);
            fingers[0][0].rotation.z=degToRad(-dat_json.if_tip);
            fingers[1][2].rotation.y=degToRad(-dat_json.mf_base_rot);
            fingers[1][2].rotation.z=degToRad(-dat_json.mf_base);
            fingers[1][1].rotation.z=degToRad(-dat_json.mf_middle);
            fingers[1][0].rotation.z=degToRad(-dat_json.mf_tip);
            fingers[2][2].rotation.y=degToRad(-dat_json.rf_base_rot);
            fingers[2][2].rotation.z=degToRad(-dat_json.rf_base);
            fingers[2][1].rotation.z=degToRad(-dat_json.rf_middle);
            fingers[2][0].rotation.z=degToRad(-dat_json.rf_tip);
            fingers[3][2].rotation.y=degToRad(-dat_json.p_base_rot);
            fingers[3][2].rotation.z=degToRad(-dat_json.p_base);
            fingers[3][1].rotation.z=degToRad(-dat_json.p_middle);
            fingers[3][0].rotation.z=degToRad(-dat_json.p_tip);
            fingers[4][2].rotation.y=degToRad(-dat_json.th_rot_orthogonal);
            fingers[4][2].rotation.x=degToRad(-dat_json.th_rot_palm);
            fingers[4][1].rotation.z=degToRad(-dat_json.th_base);
            fingers[4][0].rotation.z=degToRad(-dat_json.th_tip);
        } catch (e) {}

        if (resizeRendererToDisplaySize(renderer)) {
            camera.aspect = window.innerWidth / window.innerHeight;
            camera.updateProjectionMatrix();
        }
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
    let ret =new THREE.Vector3();
    ret.x=blenderCoords.x*100;
    ret.y=blenderCoords.z*100;
    ret.z=blenderCoords.y*-100;
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
