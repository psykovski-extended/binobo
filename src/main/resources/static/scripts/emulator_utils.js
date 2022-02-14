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
                    apply_filter_to_data(dat_json_buffer[dat_json_buffer.length-1])
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
        url:"https://localhost/roboData/rest_api/" + token + "/delete_all_to_synchronize"
    })
}

function copy_token(){
    let node = document.querySelector("#token_input");
    node.select();
    document.execCommand("copy");
}

function apply_filter_to_data(data_to_filter){
    data_to_filter.p_tip = 0.95 * data_to_filter.p_tip + 0.05 * last_dat_json.p_tip;
    data_to_filter.p_middle = 0.95 * data_to_filter.p_middle + 0.05 * last_dat_json.p_middle;
    data_to_filter.p_base = 0.95 * data_to_filter.p_base + 0.05 * last_dat_json.p_base;
    data_to_filter.p_base_rot = 0.95 * data_to_filter.p_base_rot + 0.05 * last_dat_json.p_base_rot;

    data_to_filter.rf_tip = 0.95 * data_to_filter.rf_tip + 0.05 * last_dat_json.rf_tip;
    data_to_filter.rf_middle = 0.95 * data_to_filter.rf_middle + 0.05 * last_dat_json.rf_middle;
    data_to_filter.rf_base = 0.95 * data_to_filter.rf_base + 0.05 * last_dat_json.rf_base;
    data_to_filter.rf_base_rot = 0.95 * data_to_filter.rf_base_rot + 0.05 * last_dat_json.rf_base_rot;

    data_to_filter.mf_tip = 0.95 * data_to_filter.mf_tip + 0.05 * last_dat_json.mf_tip;
    data_to_filter.mf_middle = 0.95 * data_to_filter.mf_middle + 0.05 * last_dat_json.mf_middle;
    data_to_filter.mf_base = 0.95 * data_to_filter.mf_base + 0.05 * last_dat_json.mf_base;
    data_to_filter.mf_base_rot = 0.95 * data_to_filter.mf_base_rot + 0.05 * last_dat_json.mf_base_rot;

    data_to_filter.if_tip = 0.95 * data_to_filter.if_tip + 0.05 * last_dat_json.if_tip;
    data_to_filter.if_middle = 0.95 * data_to_filter.if_middle + 0.05 * last_dat_json.if_middle;
    data_to_filter.if_base = 0.95 * data_to_filter.if_base + 0.05 * last_dat_json.if_base;
    data_to_filter.if_base_rot = 0.95 * data_to_filter.if_base_rot + 0.05 * last_dat_json.if_base_rot;

    data_to_filter.th_tip = 0.95 * data_to_filter.th_tip + 0.05 * last_dat_json.th_tip;
    data_to_filter.th_base = 0.95 * data_to_filter.th_base + 0.05 * last_dat_json.th_base;
    data_to_filter.th_rot_orthogonal = 0.95 * data_to_filter.th_rot_orthogonal + 0.05 * last_dat_json.th_rot_orthogonal;
    data_to_filter.th_rot_palm = 0.95 * data_to_filter.th_rot_palm + 0.05 * last_dat_json.th_rot_palm;

    data_to_filter.wr_bf = 0.95 * data_to_filter.wr_bf + 0.05 * last_dat_json.wr_bf;
    data_to_filter.wr_lr = 0.95 * data_to_filter.wr_lr + 0.05 * last_dat_json.wr_lr;

    last_dat_json = data_to_filter;
}
