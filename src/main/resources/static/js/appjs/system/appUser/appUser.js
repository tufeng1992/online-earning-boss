
var prefix = "/system/appUser"
$(function() {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix + "/list", // 服务器数据的加载地址
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
                        id:$('#id').val(),
                        parentId:$('#parentId').val(),
                        nikeName:$('#nikeName').val(),
                        mobile:$('#mobile').val(),
                        name:$('#name').val(),
                        role:$('#role').val(),
                        memberLevel:$('#memberLevel').val(),
                        bindStatus:$('#bindStatus').val(),
                        firstRecharge:$('#firstRecharge').val(),
                        blackFlag:$('#blackFlag').val(),
                        referralCode:$('#referralCode').val(),
                        saleId:$('#saleId').val(),
                        email:$('#email').val(),
                        deviceNumber:$('#deviceNumber').val(),
                        haveDeviceNumber:$('#haveDeviceNumber').val(),
                        accountNumber:$('#accountNumber').val(),
                        bankCode:$('#bankCode').val(),
                        sdkType:$('#sdkType').val()
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
                        field : 'blackFlagStr',
                        title : '是否黑名单'
                    },
                    {
                        field : 'id',
                        title : '用户id'
                    },
                    {
                        field : 'saleMobile',
                        title : '运营编号'
                    },
                    {
                        field : 'mobile',
                        title : '手机号'
                    },
                    {
                        field : 'nikeName',
                        title : '昵称'
                    },
                    {
                        field : 'memberLevel',
                        title : '会员vip等级  '
                    },
                    {
                        field : 'balance',
                        title : '余额'
                    },
                    {    field: 'withdrawalTotalAmount',
                        title: '提现总金额'
                    },
                    {    field: 'rechargeTotalAmount',
                        title: '充值总金额'
                    },
                    {
                        field : 'referralCode',
                        title : '邀请码'
                    },
                    {
                        field : 'parentId',
                        title : '上级代理人id'
                    },
                    {
                        field: 'topParentId',
                        title: '源头id'
                    },
                    {
                        field : 'roleName',
                        title : '角色'
                    },
                    {
                        field : 'password',
                        title : '登陆密码'
                    },
                    {
                        field : 'fundPassword',
                        title : '交易密码'
                    },
                    {
                        field : 'accountNumber',
                        title : '银行卡账号'
                    },
                    {
                        field : 'accountPhone',
                        title : '银行卡关联手机'
                    },
                    {
                        field : 'bankName',
                        title : '银行名称'
                    },
                    {
                        field : 'bankCode',
                        title : '银行代码'
                    },
                    {
                        field : 'name',
                        title : '银行卡申请人名字'
                    },
                    {
                        field : 'email',
                        title : '邮箱'
                    },
                    {
                        field : 'address',
                        title : '地址'
                    },
                    {
                        field : 'bindStatusStr',
                        title : '绑卡状态'
                    },
                    {
                        field : 'bindTime',
                        title : '绑卡时间'
                    },
                    {
                        field : 'saleId',
                        title : '销售人员id'
                    },
                    {
                        field : 'appId',
                        title : '注册渠道'
                    },
                    {
                        field : 'sdkType',
                        title : '注册sdk类型'
                    },
                    {
                        field : 'createTime',
                        title : '创建时间'
                    },
                    {
                        field : 'updateTime',
                        title : '更新时间'
                    },
                    {
                        field : 'deviceNumber',
                        title : '设备号'
                    },
                    {
                        field : 'firstRechargeStr',
                        title : '是否已完成首冲'
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
                                + row.id
                                + '\')"><i class="fa fa-key"></i></a> ';
                            var guestbook_a = '<a class="btn btn-primary btn-sm '+guest_book_a+'" href="#" mce_href="#" title="留言" onclick="addGuest(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            return e + d + guestbook_a ;
                        }
                    }]
            });
}
function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function addGuest(userId) {
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : '/system/guestbook/add?guestbookTargetId=' + userId // iframe的url
    });
}


function add() {
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/add' // iframe的url
    });
}
function edit(id) {
    layer.open({
        type : 2,
        title : '编辑',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/edit/' + id // iframe的url
    });
}
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : prefix+"/remove",
            type : "post",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code==0) {
                    layer.msg(r.msg);
                    reLoad();
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}
function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function(i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type : 'POST',
            data : {
                "ids" : ids
            },
            url : prefix + '/batchRemove',
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {

    });
}