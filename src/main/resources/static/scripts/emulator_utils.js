async function retrieveData(token){
    $.ajax({
        type:"GET",
        url: "http://localhost:1443/roboData/rest_api/" + token + "/retrieve_all_data",
        success: function(data) {
            for(i of data) {
                if(i !== undefined)
                    dat_json_buffer[dat_json_buffer.length] = i;
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(errorThrown)
        },
        dataType: "json"
    });
}
async function yieldData() {
    const data = await retrieveData(token);
    console.log(data)
    if(data !== undefined && data.length > 0){
        for(i of data) {
            if(i !== undefined)
                dat_json_buffer[dat_json_buffer.length] = i;
        }
    }
}

function periodicRestCall(){
    setInterval(() => {
        retrieveData(token).catch(e => console.log(e));
    }, 50)
}
