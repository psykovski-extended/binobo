function disconnect(node){
    node.onclick = () => connect(node);
    node.classList.remove("connected");
    dat_json_buffer = [];

    socket.close();

    document.getElementById("connect").innerHTML = "connect";
}

function connect(node) {
    node.onclick = () => disconnect(node);
    node.classList.add("connected");

    socket = new SockJS('/realtime-emulator');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/publish-data/' + token, res => {
            let data = JSON.parse(res.body);
            for(i of data) {
                if(i !== undefined) {
                    dat_json_buffer[dat_json_buffer.length] = i;
                    //apply_filter_to_data(dat_json_buffer[dat_json_buffer.length-1])
                }
            }
        });
    });
    document.getElementById("connect").innerHTML = "stop";
}

function sync() {
    dat_json_buffer = [];
    $.ajax({
        type:"Delete",
        url: window.location.protocol + "//" + window.location.hostname + "/roboData/rest_api/" + token + "/delete_all_to_synchronize"
    })
}

function copy_token(){
    let node = document.querySelector("#token_input");
    node.select();
    document.execCommand("copy");
}

function apply_filter_to_data(data_to_filter){
    let d = 0.6;
    let d_m1 = 1 - d;
    data_to_filter.p_tip = d * data_to_filter.p_tip + d_m1 * last_dat_json.p_tip;
    data_to_filter.p_middle = d * data_to_filter.p_middle + d_m1 * last_dat_json.p_middle;
    data_to_filter.p_base = d * data_to_filter.p_base + d_m1 * last_dat_json.p_base;
    data_to_filter.p_base_rot = d * data_to_filter.p_base_rot + d_m1 * last_dat_json.p_base_rot;

    data_to_filter.rf_tip = d * data_to_filter.rf_tip + d_m1 * last_dat_json.rf_tip;
    data_to_filter.rf_middle = d * data_to_filter.rf_middle + d_m1 * last_dat_json.rf_middle;
    data_to_filter.rf_base = d * data_to_filter.rf_base + d_m1 * last_dat_json.rf_base;
    data_to_filter.rf_base_rot = d * data_to_filter.rf_base_rot + d_m1 * last_dat_json.rf_base_rot;

    data_to_filter.mf_tip = d * data_to_filter.mf_tip + d_m1 * last_dat_json.mf_tip;
    data_to_filter.mf_middle = d * data_to_filter.mf_middle + d_m1 * last_dat_json.mf_middle;
    data_to_filter.mf_base = d * data_to_filter.mf_base + d_m1 * last_dat_json.mf_base;
    data_to_filter.mf_base_rot = d * data_to_filter.mf_base_rot + d_m1 * last_dat_json.mf_base_rot;

    data_to_filter.if_tip = d * data_to_filter.if_tip + d_m1 * last_dat_json.if_tip;
    data_to_filter.if_middle = d * data_to_filter.if_middle + d_m1 * last_dat_json.if_middle;
    data_to_filter.if_base = d * data_to_filter.if_base + d_m1 * last_dat_json.if_base;
    data_to_filter.if_base_rot = d * data_to_filter.if_base_rot + d_m1 * last_dat_json.if_base_rot;

    data_to_filter.th_tip = d * data_to_filter.th_tip + d_m1 * last_dat_json.th_tip;
    data_to_filter.th_base = d * data_to_filter.th_base + d_m1 * last_dat_json.th_base;
    data_to_filter.th_rot_orthogonal = d * data_to_filter.th_rot_orthogonal + d_m1 * last_dat_json.th_rot_orthogonal;
    data_to_filter.th_rot_palm = d * data_to_filter.th_rot_palm + d_m1 * last_dat_json.th_rot_palm;

    data_to_filter.wr_bf = d * data_to_filter.wr_bf + d_m1 * last_dat_json.wr_bf;
    data_to_filter.wr_lr = d * data_to_filter.wr_lr + d_m1 * last_dat_json.wr_lr;

    last_dat_json = data_to_filter;
}
