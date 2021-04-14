/**
 * 系统提现总开关
 */
function sysWithdrawalCheckClick() {
    layer.confirm('确定切换[系统提现开关]？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/system/pay/paySwitchUpdate",
            data : {
                'cacheKey': 'SYS_WITHDRAWAL_CHECK',
                'cacheValue': $("input[type='radio'][name='sysWithdrawalCheck']:checked").val()
            },
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg('切换成功！');
                } else {
                    layer.msg('切换失败，请刷新页面重试！');
                }
            }
        });
    })
}

/**
 * 系统充值总开关
 */
function paySwitchClick() {
    layer.confirm('确定切换[系统充值开关]？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/system/pay/paySwitchUpdate",
            data : {
                'cacheKey': 'PAY_SWITCH',
                'cacheValue': $("input[type='radio'][name='paySwitch']:checked").val()
            },
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg('切换成功！');
                } else {
                    layer.msg('切换失败，请刷新页面重试！');
                }
            }
        });
    })
}

/**
 * 提现审批开关
 */
function applySwitchClick() {
    layer.confirm('确定切换[提现审批开关]？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/system/pay/paySwitchUpdate",
            data : {
                'cacheKey': 'APPLY_SWITCH',
                'cacheValue': $("input[type='radio'][name='applySwitch']:checked").val()
            },
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg('切换成功！');
                } else {
                    layer.msg('切换失败，请刷新页面重试！');
                }
            }
        });
    })
}

/**
 * 邀请减半开关
 */
function inviteFlagHalfClick() {
    layer.confirm('确定切换[邀请减半开关]？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/system/pay/paySwitchUpdate",
            data : {
                'cacheKey': 'INVITE_FLAG_HALF',
                'cacheValue': $("#inviteFlagHalf option:selected").val()
            },
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg('切换成功！');
                } else {
                    layer.msg('切换失败，请刷新页面重试！');
                }
            }
        });
    })
}

/**
 * 价格随机区间
 */
function sdPriceClick(ind) {
    layer.confirm('确定切换[商品'+ind+']价格区间？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/system/pay/changeSdPrice",
            data : {
                'index': ind,
                'priceMin': $("#priceMin"+ind).val(),
                'priceMax': $("#priceMax"+ind).val()
            },
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg('切换成功！');
                } else {
                    layer.msg('切换失败，请刷新页面重试！');
                }
            }
        });
    })
}

/**
 * 充值选择开关
 */
function payChannelBranchClick() {
    layer.confirm('确定切换[充值四方开关]？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/system/pay/paySwitchUpdate",
            data : {
                'cacheKey': 'PAY_CHANNEL_BRANCH',
                'cacheValue': $("#payChannelBranch option:selected").val()
            },
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg('切换成功！');
                } else {
                    layer.msg('切换失败，请刷新页面重试！');
                }
            }
        });
    })
}

/**
 * 提现选择开关
 */
function payoutChannelBranchClick() {
    layer.confirm('确定切换[提现四方开关]？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/system/pay/paySwitchUpdate",
            data : {
                'cacheKey': 'PAYOUT_CHANNEL_BRANCH',
                'cacheValue': $("#payoutChannelBranch option:selected").val()
            },
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg('切换成功！');
                } else {
                    layer.msg('切换失败，请刷新页面重试！');
                }
            }
        });
    })
}

/**
 * 封盘开关
 */
function goPaySwitchClick() {
    layer.confirm('确定切换[系统升级调整开关]？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/system/pay/paySwitchUpdate",
            data : {
                'cacheKey': 'GO_PAY_SWITCH',
                'cacheValue': $("input[type='radio'][name='goPaySwitch']:checked").val()
            },
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg('切换成功！');
                } else {
                    layer.msg('切换失败，请刷新页面重试！');
                }
            }
        });
    })
}