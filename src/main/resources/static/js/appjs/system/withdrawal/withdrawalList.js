var prefix = "/system/withdrawal"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        userId:$('#userId').val(),
                        mobile:$('#mobile').val(),
                        id:$('#id').val(),
                        saleNo:$('#saleNo').val(),
                        applyStatus:$('#applyStatus').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        field: 'id',
                        title: '提现订单id'
                    },
                    {
                        field: 'orderNo',
                        title: '提现订单编号'
                    },
                    {
                        field: 'saleNo',
                        title: '运营编号'
                    },
                    {
                        field: 'mobile',
                        title: '用户手机号'
                    },
                    {
                        field: 'userId',
                        title: '用户id'
                    },
                    {
                        field: 'parentId',
                        title: '上级代理人id'
                    },
                    {
                        field: 'regDate',
                        title: '注册时间'
                    },
                    {
                        field: 'rechargeAmount',
                        title: '充值总额'
                    },
                    {
                        field: 'lastRechargeDate',
                        title: '上次充值时间'
                    },
                    {
                        field: 'withdrawalAmount',
                        title: '提现金额'
                    },
                    {
                        field: 'withdrawalApplyTime',
                        title: '提现申请时间'
                    },
                    {
                        field: 'withdrawalAuditStatusDesc',
                        title: '提现审核状态'
                    },
                    {
                        field: 'failReason',
                        title: '审核失败备注'
                    },
                    {
                        field: 'withdrawalAuditTime',
                        title: '提现审核时间'
                    },
                    {
                        title : '审核成功',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            if(row.withdrawalAuditStatus == 1){
                                var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="审核成功" onclick="auditSuccess(\''
                                    + row.orderNo
                                    + '\')"><i class="fa fa-edit"></i></a> ';
                                return e;
                            }
                            return '';
                        }
                    },
                    {
                        title : '审核失败',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            if(row.withdrawalAuditStatus == 1){
                                var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="审核失败" onclick="auditFail(\''
                                    + row.orderNo
                                    + '\')"><i class="fa fa-edit"></i></a> ';
                                return e;
                            }
                            return '';
                        }
                    }]
            });
}

function auditSuccess(orderNo) {
    layer.confirm('确定审核通过该提现记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/auditSuccess",
            type: "post",
            data: {
                'orderNo': orderNo
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function auditFail(orderNo) {
    layer.open({
        type: 2,
        title: '审核失败',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/auditFail/' + orderNo // iframe的url
    });
}