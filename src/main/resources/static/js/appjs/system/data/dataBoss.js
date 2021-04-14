$(function() {
    load();
});

function editData(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: true, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: '/system/data/dataEdit/' + id // iframe的url
    });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : "/system/data/dataBossList", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : false, // 设置为true会在底部显示分页条
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
                        //limit: params.limit,
                        //offset:params.offset,
                        //key:$('#key').val(),
                        //keyExplain:$('#keyExplain').val()
                        // username:$('#searchName').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns : [
                    {
                        field : 'date',
                        title : '日期'
                    },
                    {
                        field : 'vipCount',
                        title : '会员总数'
                    },
                    {
                        field : 'localUserCount',
                        title : '新增用户'
                    },
                    {
                        field : 'userReferral',
                        title : '用户裂变'
                    },
                    {
                        field : 'saleReferral',
                        title : '客服推广'
                    },
                    {
                        field : 'netProfit',
                        title : '净利'
                    },
                    {
                        field : 'rechargeCount',
                        title : '充值笔数'
                    },
                    {
                        field : 'withdrawCount',
                        title : '提现笔数'
                    },
                    {
                        field : 'rechargeAmount',
                        title : '实际充值金额'
                    },
                    {
                        field : 'withdrawAmount',
                        title : '实际提现金额'
                    },
                    {
                        field : 'financialProfitInAmount',
                        title : '理财转入'
                    },
                    {
                        field : 'financialProfitAmount',
                        title : '理财收益'
                    },
                    {
                        field : 'financialProfitOutAmount',
                        title : '理财转出'
                    },
                    {
                        field : 'commissionsAmount',
                        title : '任务奖励'
                    },
                    {
                        field : 'vipPayCount',
                        title : '购买vip单数'
                    },
                    {
                        field : 'vipPayAmount',
                        title : '购买vip金额'
                    },
                    {
                        field : 'firstRechargeAmount',
                        title : '首充金额'
                    },
                    {
                        field : 'vipBalanceCount',
                        title : 'app余额总和'
                    },
                    {
                        field : 'financialProfitCountAmount',
                        title : '理财本金余额'
                    },
                    {
                        title : '编辑',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm show" href="#" mce_href="#" title="编辑" onclick="editData(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            return e;
                        }
                    } ]
            });
}