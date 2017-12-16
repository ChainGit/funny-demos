document.write('<script src="js/config.js"><\/script>');

function sendData(api, fun, args) {
    if (args == null || args == "" || args == undefined) {
        args = {};
    } else {
        args = JSON.stringify(args);
    }
    args = {
        "ns": args,
    };
    var url = base + api;
    //console.log(args);
    $.post(url, args, success);

    function success(data, status, xhr) {
        if (data) {
            //console.log(data);
            return fun(data);
        }
    }
}


function isEmpty(obj) {
    if (obj == null || obj == undefined)
        obj = "";
    else if (typeof obj == "object")
        obj = JSON.stringify(obj);
    return obj;
}