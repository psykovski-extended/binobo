let data = [];

let last_filtered_data = [0,0,0,0,
                            0,0,0,0,
                            0,0,0,0,
                            0,0,0,0,
                            0,0,0,0,
                            0,0];

let data_buffer = [];

let token = document.getElementById("token").value;

let socket;

let socket_ping;

let socket_ping_receiver;

let socket_ping_interval;
