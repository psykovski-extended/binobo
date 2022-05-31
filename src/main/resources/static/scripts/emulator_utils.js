function disconnect(node){
    node.onclick = () => connect(node);
    node.classList.remove("connected");
    data_buffer = [];

    socket.send('close session');
    socket.close();

    clearInterval(socket_ping_interval);
    clearInterval(data_shifter_interval); // data_shifter_interval

    socket_ping.close();
    socket_ping_receiver.close();

    document.getElementById("connect").innerHTML = "connect";
}

function connect(node) {
    node.onclick = () => disconnect(node);
    node.classList.add("connected");

    socket = new WebSocket('wss://emulator.binobo.io/' + token);
    socket_ping = new WebSocket('wss://emulator.binobo.io/');
    socket_ping_receiver = new WebSocket('wss://emulator.binobo.io/ping_' + token);

    socket_ping_receiver.addEventListener('message', ({ data }) => {
        let x = new Date();
        let ping = x.getTime() - eval(data);
        let node = document.getElementById("ping")
        node.innerHTML = ping + "ms"
    });

    socket_ping.addEventListener('open', evt => {
        socket_ping_interval = setInterval(() => {
            socket_ping.send('["ping_' + token + '",' + (new Date()).getTime() + "]");
        }, 10000);
        setTimeout(() => {
            socket_ping.send('["ping_' + token + '",' + (new Date()).getTime() + "]");
        }, 1000);
        data_shifter_interval = setInterval(() => {
            let data = data_buffer.shift();
            if(data !== undefined)
                unityInstance.SendMessage("Hand_new", "parseHndValues", data + "");
        }, 33)
    });

    socket_ping_receiver.addEventListener('open', evt => {
        socket_ping_receiver.send('ping receiver for: ' + token)
    });

    socket.addEventListener('message', ({data}) => {
        try {
            for(i of eval(data)){
                data_buffer[data_buffer.length] = i;
                console.log(i);
            }
        } catch (e) {
            console.log('wrong data-format received: ' + data)
        }
    });
    socket.addEventListener('open', evt => {
        socket.send('receiver for: ' + token)
    })
    document.getElementById("connect").innerHTML = "stop";
}

function sync() {
    data_buffer = [];
}

function copy_token(){
    let node = document.querySelector("#token_input");
    node.select();
    document.execCommand("copy");
}

function apply_filter_to_data(data_to_filter){
    let w = 0.6;
    let w_m1 = 1 - w;
    let filtered_data = [];

    for(let i = 0; i<data_to_filter.length; i++){
        filtered_data[i] = w * data_to_filter[i] + w_m1 * last_filtered_data[i]
    }
    last_filtered_data = filtered_data;
    return filtered_data;
}
