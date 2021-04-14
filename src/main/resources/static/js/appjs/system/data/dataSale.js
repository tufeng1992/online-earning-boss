$(function() {
    load();
    $('#startTime').datetimepicker({
        language: 'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        minView:0,
        forceParse: 1,
        showMeridian: 0
    });

    $('#endTime').datetimepicker({
        language: 'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        minView:0,
        forceParse: 1,
        showMeridian: 0
    });
    function getLocalTime(nS) {
        return new Date((parseInt(nS) + 60 * 60 * 4) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/[\u4e00-\u9fa5]/g, "");
    };
    $("#startTime").on("changeDate", function(e) {$('#endTime').datetimepicker('setStartDate', getLocalTime(e.date.valueOf() / 1000));});
    $("#endTime").on("changeDate", function(e) {$('#startTime').datetimepicker('setEndDate', getLocalTime(e.date.valueOf() / 1000));});
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : "/system/data/dataSale/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect : false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageNumber : 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset:params.offset,
                        operateNo:$('#operateNo').val(),
                        userMobile:$('#userMobile').val(),
                        userId:$('#userId').val(),
                        userLevel:$('#userLevel').val(),
                        startTime:$('#startTime').val(),
                        endTime:$('#endTime').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns : [
                    // {
                    //     field : 'date',
                    //     title : '日期'
                    // },
                    {
                        field : 'id',
                        title : ' 用户id'
                    },
                    {
                        field : 'saleNo',
                        title : ' 运营编号'
                    },
                    {
                        field : 'userLevel',
                        title : ' 用户层级'
                    },
                    {
                        field : 'mobile',
                        title : '手机号'
                    },
                    {
                        field : 'name',
                        title : ' 名称'
                    },
                    {
                        field : 'level',
                        title : ' 会员等级'
                    },
                    {
                        field : 'totalAssets',
                        title : ' 总资产'
                    },
                    {
                        field : 'rechargeAmount',
                        title : ' 充值金额'
                    },
                    {
                        field : 'withdrawalAmount',
                        title : ' 提现金额'
                    },
                    {
                        field : 'vaildCount',
                        title : ' 有效推广人数'
                    },
                    {
                        field : 'totalCount',
                        title : ' 总推广人数'
                    },
                    {
                        field : 'clickCount',
                        title : ' 刷单数'
                    },
                    {
                        field : 'clickCommission',
                        title : ' 刷单佣金'
                    },
                    {
                        field : 'superiorId',
                        title : ' 上级id'
                    },
                    {
                        field : 'regDate',
                        title : ' 注册时间'
                    },
                    {
                        title : '下级详情',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            var v = '<a class="btn btn-success btn-sm" href="#" title="下级用户列表"  mce_href="#" onclick="read(\'' + row.id + '\')">详情</a> ';
                            return v ;
                        }
                    }
                    ]
            });
}
function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}
function read(id) {
    layer.open({
        type : 2,
        title : '详情',
        maxmin : true,
        shadeClose : false,
        area : [ '800px', '550px' ],
        content : '/system/data/dataSale/read/' + id
    });
}
function exportExcel() {
    alert("确认导出当前数据，点击确定后稍等，excel生成需要时间，请不要重复点击");
    var operateNo = $('#operateNo').val();
    var userMobile = $('#userMobile').val();
    var userId = $('#userId').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    //console.log("downloadFile..........................");
    location.href = "/system/data/dataSale/export?operateNo="+operateNo+"&userMobile="+userMobile+
        "&userId="+userId+ "&startTime="+startTime+ "&endTime="+endTime;

}