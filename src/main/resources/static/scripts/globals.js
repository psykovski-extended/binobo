let dat_json = {
    "p_tip": 0,
    "p_middle": 0,
    "p_base": 0,
    "p_base_rot": 0,
    "rf_tip": 0,
    "rf_middle": 0,
    "rf_base": 0,
    "rf_base_rot": 0,
    "mf_tip": 0,
    "mf_middle": 0,
    "mf_base": 0,
    "mf_base_rot": 0,
    "if_tip": 0,
    "if_middle": 0,
    "if_base": 0,
    "if_base_rot": 0,
    "th_tip": 0,
    "th_base": 0,
    "th_rot_orthogonal": 0,
    "th_rot_palm": 0,
    "wr_bf": 0,
    "wr_lr": 0
};

let last_dat_json = {
    "p_tip": 0,
    "p_middle": 0,
    "p_base": 0,
    "p_base_rot": 0,
    "rf_tip": 0,
    "rf_middle": 0,
    "rf_base": 0,
    "rf_base_rot": 0,
    "mf_tip": 0,
    "mf_middle": 0,
    "mf_base": 0,
    "mf_base_rot": 0,
    "if_tip": 0,
    "if_middle": 0,
    "if_base": 0,
    "if_base_rot": 0,
    "th_tip": 0,
    "th_base": 0,
    "th_rot_orthogonal": 0,
    "th_rot_palm": 0,
    "wr_bf": 0,
    "wr_lr": 0
};

let dat_json_buffer = [];

let token = document.getElementById("token").value;

let stompClient = null;
let socket;
