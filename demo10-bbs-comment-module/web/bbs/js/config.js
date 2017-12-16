// var base = "http://192.168.43.160:8080";
// var base = "http://localhost:8080"
var base = "http://192.168.56.2:8080"

var cookieName = "bbs_demo";

var api = {

    user: {
        signup: "/user/signup",
        signin: "/user/signin",
        briefInfo: "/user/briefInfo",
        lastComment: "/user/briefInfo",
    },

    comment: {
        main: {
            add: "/comment/main/add",
            delete: "/comment/main/delete",
            find: "/comment/main/find",
        },
        reply: {
            add: "/comment/reply/add",
            delete: "/comment/reply/delete",
            find: "/comment/reply/find",
        },
    },

};