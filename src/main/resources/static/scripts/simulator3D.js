import {OrbitControls} from './lib/three.js-master/examples/jsm/controls/OrbitControls.js';
import {FBXLoader} from "./lib/three.js-master/examples/jsm/loaders/FBXLoader.js";

let dat_json = {
    "p_tip": 0,
    "p_middle": 0,
    "p_base": 0,
    "p_base_rot": 0,
    "rf_tip": 0,
    "rf_middle": 0,
    "rf_base": 0,
    "rf_base_rot":0,
    "mf_tip": 0,
    "mf_middle": 0,
    "mf_base": 0,
    "mf_base_rot":0,
    "if_tip": 0,
    "if_middle": 0,
    "if_base": 0,
    "if_base_rot":0,
    "th_tip": 0,
    "th_base": 0,
    "th_rot_orthogonal": 0,
    "th_rot_palm": 0,
    "wr_bf": 0,
    "wr_lr": 0
};

let dat_json_buffer = [];
await retrieveData();
sim3D();

function sim3D() {
    let canvas = document.querySelector('#c'); //Three.js canvas to output the camera view as an image
    const renderer = new THREE.WebGLRenderer({antialias: true, canvas}); //renderer
    renderer.setSize(canvas.innerWidth, canvas.innerHeight);
//camera settings and setup
    const fov = 45;
    let aspect = window.innerWidth / window.innerHeight;  // the canvas default
//camera clipping planes
    const near = 1;
    const far = 10000;
    let camera = new THREE.PerspectiveCamera(fov, aspect, near, far);

//document.requestFullscreen();//this doesn't work because browsers are not stupid
//scene setup
    const scene = new THREE.Scene();
    scene.add(camera)
    scene.background = new THREE.Color('#222');//apparently the same color as Opera mobile-dark mode
    const controls = new OrbitControls(camera, renderer.domElement)
    controls.maxPolarAngle = degToRad(160);
    controls.minPolarAngle = degToRad(20);
    camera.position.set(500, 100, 200);
    controls.update();
    const dir = new THREE.DirectionalLight(0xffffff, 1)
    const ambient = new THREE.AmbientLight(0x404040, 2.5);
    scene.add(dir);
    scene.add(ambient);
    /*
    // Alternative .obj loader, but I prefer my models with at least some materials
    var objLoader = new OBJLoader();


    objLoader.load( 'models/Astronaut.obj', function ( object ) {
        scene.add( object );
    });
    */


// this is the thing that loads .fbx-files, which took way too long to set up
    let fbxloader = new FBXLoader();
    /*
    fbxloader.load('models/SampleShipBloom.fbx', function (object) {


        scene.add(object);
    })
     */
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
    const fpos = [
        [   blenderToThree(new THREE.Vector3(2.38672, 0.434034, 0.00079)),
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
        ]];

    function loadSegment( object, d, s) {
        switch (s) {
            case 1:
                let point = new THREE.Object3D();

                fingers[d - 1][1].add(point)
                point.add(object)
                fingers[d - 1][0] = point;
                point.position.x = fpos[d - 1][0].x - fpos[d - 1][1].x;
                point.position.y = fpos[d - 1][0].y - fpos[d - 1][1].y;
                point.position.z = fpos[d - 1][0].z - fpos[d - 1][1].z;

                break;
            case 2:
                let point2 = new THREE.Object3D();

                fingers[d - 1][2].add(point2)
                point2.add(object)
                fingers[d - 1][1] = point2;
                point2.position.x = fpos[d - 1][1].x - fpos[d - 1][2].x;
                point2.position.y = fpos[d - 1][1].y - fpos[d - 1][2].y;
                point2.position.z = fpos[d - 1][1].z - fpos[d - 1][2].z;

                break;
            case 3:
                let point3 = new THREE.Object3D();

                palm.add(point3)
                point3.add(object)
                fingers[d - 1][2] = point3;
                point3.position.x = fpos[d - 1][2].x;
                point3.position.y = fpos[d - 1][2].y;
                point3.position.z = fpos[d - 1][2].z;

                break;
            default:
                break;
        }

        object.material = new THREE.MeshStandardMaterial();

    }

    function loadFingers() {
        for (let s = 3; s > 0; s--)
            for (let d = 1; d < 6; d++) {
                fbxloader.load('scripts/simulatorModels/simHand_id' + d + '' + s + '.fbx',  object => {
                    object.name = namePart(d, s);
                    loadSegment(object, d, s);
                });
            }
    }

    function namePart(d, s) {
        return 'digit' + d + 'segment' + s;
    }
    let raycaster = new THREE.Raycaster();
    let mouse = new THREE.Vector2();

    // canvas.addEventListener('mousemove', onMouseMove, false)
    canvas.onmousemove = event => onMouseMove(event);
    // window.addEventListener('mousedown',onMouseDown, false);
    canvas.onclick = event => onMouseDown(event);

    function onMouseMove(event) {

    }

    let lastsel=0;
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
     this is important for the view to not break

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
     render - void

     the almighty render function
     */
    function render() {
        /*
        rotateTowardsZ(fingers[0][2],degToRad(-70),0.001);
        rotateTowardsZ(fingers[0][1],degToRad(-70),0.001);
        rotateTowardsZ(fingers[0][0],degToRad(-70),0.001);
        rotateTowardsZ(fingers[1][2],degToRad(-70),0.001);
        rotateTowardsZ(fingers[1][1],degToRad(-70),0.001);
        rotateTowardsZ(fingers[1][0],degToRad(-70),0.001);
        rotateTowardsZ(fingers[2][2],degToRad(-70),0.001);
        rotateTowardsZ(fingers[2][1],degToRad(-70),0.001);
        rotateTowardsZ(fingers[2][0],degToRad(-70),0.001);
        rotateTowardsZ(fingers[3][2],degToRad(-70),0.001);
        rotateTowardsZ(fingers[3][1],degToRad(-70),0.001);
        rotateTowardsZ(fingers[3][0],degToRad(-70),0.001);
        rotateTowardsZ(fingers[4][2],degToRad(-25),0.001);
        rotateTowardsX(fingers[4][2],degToRad(-85),0.002);
        rotateTowardsZ(fingers[4][1],degToRad(-10),0.001);
        rotateTowardsZ(fingers[4][0],degToRad(-40),0.001);
         */
        dat_json = dat_json_buffer.shift() || dat_json;

        if(!dat_json_buffer[5]){
            retrieveData();
        }

        wrist.rotation.x=degToRad(dat_json.wr_lr);
        wrist.rotation.z=degToRad(dat_json.wr_bf);
        fingers[0][2].rotation.y=degToRad(dat_json.if_base_rot);
        fingers[0][2].rotation.z=degToRad(dat_json.if_base);
        fingers[0][1].rotation.z=degToRad(dat_json.if_middle);
        fingers[0][0].rotation.z=degToRad(dat_json.if_tip);
        fingers[1][2].rotation.y=degToRad(dat_json.mf_base_rot);
        fingers[1][2].rotation.z=degToRad(dat_json.mf_base);
        fingers[1][1].rotation.z=degToRad(dat_json.mf_middle);
        fingers[1][0].rotation.z=degToRad(dat_json.mf_tip);
        fingers[2][2].rotation.y=degToRad(dat_json.rf_base_rot);
        fingers[2][2].rotation.z=degToRad(dat_json.rf_base);
        fingers[2][1].rotation.z=degToRad(dat_json.rf_middle);
        fingers[2][0].rotation.z=degToRad(dat_json.rf_tip);
        fingers[3][2].rotation.y=degToRad(dat_json.p_base_rot);
        fingers[3][2].rotation.z=degToRad(dat_json.p_base);
        fingers[3][1].rotation.z=degToRad(dat_json.p_middle);
        fingers[3][0].rotation.z=degToRad(dat_json.p_tip);
        fingers[4][2].rotation.y=degToRad(dat_json.th_rot_orthogonal);
        fingers[4][2].rotation.x=degToRad(dat_json.th_rot_palm);
        fingers[4][1].rotation.z=degToRad(dat_json.th_base);
        fingers[4][0].rotation.z=degToRad(dat_json.th_tip);

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
function radToDeg(rad) {
    return rad * 180 / Math.PI;
}

function degToRad(deg) {
    return deg / 180 * Math.PI;
}

function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}

function blenderToThree(blenderCoords) {
    let ret =new THREE.Vector3();
    ret.x=blenderCoords.x*100;
    ret.y=blenderCoords.z*100;
    ret.z=blenderCoords.y*-100;
    return ret;
}

async function retrieveData(){
    $.ajax({
        type:"GET",
        url: "https://localhost:1443/roboData/rest_api/7072e5f7-474f-44e6-b541-93df0d3abb87/retrieve_all_data",
        success: function(data) {
            // console.log(data);
            // $("body").append(JSON.parse(data));
            dat_json_buffer = data;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(errorThrown)
        },
        dataType: "json"
    });
}
