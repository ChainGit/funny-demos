//格式化日期
function formateDate(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    var h = date.getHours();
    var mi = date.getMinutes();
    m = m > 9 ? m : '0' + m;
    return y + '-' + m + '-' + d + ' ' + h + ':' + mi;
}

//删除节点
function removeNode(node) {
    node.parentNode.removeChild(node);
}

var layer = null;

layui.use('layer', function() {
    layer = layui.layer;
    // layer.msg('欢迎使用');
});

function test() {
    var list = document.getElementById('list');
    var boxs = list.children;
    var timer;

    /**
     * 赞分享
     * @param box 每个分享的div容器
     * @param el 点击的元素
     */
    function praiseBox(box, el) {
        var txt = el.innerHTML;
        var praisesTotal = box.getElementsByClassName('praises-total')[0];
        var oldTotal = parseInt(praisesTotal.getAttribute('total'));
        var newTotal;
        if (txt == '赞') {
            newTotal = oldTotal + 1;
            praisesTotal.setAttribute('total', newTotal);
            praisesTotal.innerHTML = (newTotal == 1) ? '我觉得很赞' : '我和' + oldTotal + '个人觉得很赞';
            el.innerHTML = '取消赞';
        } else {
            newTotal = oldTotal - 1;
            praisesTotal.setAttribute('total', newTotal);
            praisesTotal.innerHTML = (newTotal == 0) ? '' : newTotal + '个人觉得很赞';
            el.innerHTML = '赞';
        }
        praisesTotal.style.display = (newTotal == 0) ? 'none' : 'block';
    }

    /**
     * 发评论
     * @param box 每个分享的div容器
     * @param el 点击的元素
     */
    function reply(box, el) {
        var commentList = box.getElementsByClassName('comment-list')[0];
        var textarea = box.getElementsByClassName('comment')[0];
        var commentInfo = box.getElementsByClassName("commentInfo")[0];
        var commentMainInfo = box.id;
        var cs = commentMainInfo.split("_");
        var commentId = cs[0];
        var userId = myUserId;
        var textHead = 0;
        var realText = textarea.value;
        if (isEmpty(commentInfo.value) != "") {
            cs = commentInfo.value.split("_");
            commentId = cs[0];
            userId = myUserId;
            textHead = cs[2];
            realText = textarea.value.substring(textHead);
        }

        var args = {
            "userId": userId,
            "parentCommentId": commentId,
            "text": realText,
        };

        sendData(api.comment.reply.add, function(res) {
            if (res.success) {
                getCommentReplyOut(commentMainInfo.split("_")[0], commentMainInfo);
            }
        }, args);

        textarea.value = '';
        textarea.onblur();
    }

    /**
     * 赞回复
     * @param el 点击的元素
     */
    function praiseReply(el) {
        var myPraise = parseInt(el.getAttribute('my'));
        var oldTotal = parseInt(el.getAttribute('total'));
        var newTotal;
        if (myPraise == 0) {
            newTotal = oldTotal + 1;
            el.setAttribute('total', newTotal);
            el.setAttribute('my', 1);
            el.innerHTML = newTotal + ' 取消赞';
        } else {
            newTotal = oldTotal - 1;
            el.setAttribute('total', newTotal);
            el.setAttribute('my', 0);
            el.innerHTML = (newTotal == 0) ? '赞' : newTotal + ' 赞';
        }
        el.style.display = (newTotal == 0) ? '' : 'inline-block'
    }

    /**
     * 操作留言
     * @param el 点击的元素
     */
    function operate(el) {
        var commentBox = el.parentNode.parentNode.parentNode;
        var box = commentBox.parentNode.parentNode.parentNode;
        var txt = el.innerHTML;
        var user = commentBox.getElementsByClassName('user')[0].innerHTML;
        var textarea = box.getElementsByClassName('comment')[0];
        var commentInfo = box.getElementsByClassName("commentInfo")[0];
        var userName = box.getElementsByClassName("userName")[0].value;
        var commentInfoVal = commentBox.id;
        if (txt == '回复') {
            textarea.focus();
            textarea.value = '回复' + userName + "：";
            commentInfo.value = commentInfoVal + "_" + textarea.value.length;
            // textarea.onkeyup();
        } else {
            removeCommentReply(commentBox);
        }
    }

    //把事件代理到每条分享div容器
    for (var i = 0; i < boxs.length; i++) {

        //点击
        boxs[i].onclick = function(e) {
            e = e || window.event;
            var el = e.srcElement;
            switch (el.className) {

                //关闭分享
                case 'close':
                    removeNode(el.parentNode);
                    break;

                    //赞分享
                case 'praise':
                    praiseBox(el.parentNode.parentNode.parentNode, el);
                    break;

                    //回复按钮蓝
                case 'btn':
                    reply(el.parentNode.parentNode.parentNode, el);
                    break

                    //回复按钮灰
                case 'btn btn-off':
                    clearTimeout(timer);
                    break;

                    //赞留言
                case 'comment-praise':
                    praiseReply(el);
                    break;

                    //操作留言
                case 'comment-operate':
                    operate(el);
                    break;
            }
        }

        //评论
        var textArea = boxs[i].getElementsByClassName('comment')[0];

        //评论获取焦点
        textArea.onfocus = function() {
            this.parentNode.className = 'text-box text-box-on';
            this.value = this.value == '评论…' ? '' : this.value;
            // this.onkeyup();
        }

        //评论失去焦点
        textArea.onblur = function() {
            var me = this;
            var val = me.value;
            if (val == '') {
                timer = setTimeout(function() {
                    me.value = '评论…';
                    me.parentNode.className = 'text-box';
                }, 200);
                var commentInfo = me.parentNode.getElementsByClassName("commentInfo")[0];
                commentInfo.value = "";
            }
        }

        //评论按键事件
        textArea.onkeyup = function() {
            var val = this.value;
            var len = val.length;
            var els = this.parentNode.children;
            var btn = els[1];
            var word = els[2];
            if (len <= 0 || len > 140) {
                btn.className = 'btn btn-off';
            } else {
                btn.className = 'btn';
            }
            word.innerHTML = len + '/140';
        }

    }
}