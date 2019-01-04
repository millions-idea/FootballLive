window.utils = {
    response: {
        isError: function (data) {
            return data == null || data.error == null || data.error == 1;
        },
        isErrorByCode: function (data) {
            return data == null || data.code == null || data.code == 500 || data.code == 300;
        },
        isException: function (data) {
            return data != null && data.code != null && data.code == 400 ;
        }
    },
    date: {
        timestampConvert: function (ts) {
            var date = new Date(ts);
            Y = date.getFullYear() + '-';
            M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
            D = date.getDate() + ' ';
            h = date.getHours() + ':';
            m = date.getMinutes() + ':';
            s = date.getSeconds();
            return Y+M+D+h+m+s;
        }
    },
    scheduleStatus: {
        scheduleStatusInfo: function (ts) {
            switch (ts) {
                case 0:
                    return '未开始';
                    break;
                case 1:
                    return '<span  style="color: red">进行中</span> ';
                    break;
                case 2:
                    return '<span  style="color: silver">已结束</span> ';
                    break;
                case 3:
                    return '<span  style="color: silver">推迟</span> ';
                    break;
                case 4:
                    return '<span  style="color: silver">待定</span> ';
                    break;
            }
        }
    },
    type: {
        typeInfo: function (ts) {
            switch (ts) {
                case true:
                    return '图片广告';
                    break;
                case false:
                    return '视频广告';
                    break;
            }
        }
    }

}