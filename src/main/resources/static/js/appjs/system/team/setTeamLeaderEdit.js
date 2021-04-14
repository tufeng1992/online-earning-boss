$().ready(function() {
  validateRule();
});

$.validator.setDefaults({
  submitHandler : function() {
    update();
  }
});
function update() {
  $("#teamUserIds").val(getCheckedUser());
  $.ajax({
    cache : true,
    type : "POST",
    url : "/system/team/teamLeaderEditUpdate",
    data : $('#signupForm').serialize(),// 你的formid
    async : false,
    error : function(request) {
      parent.layer.alert("Connection error");
    },
    success : function(data) {
      if (data.code == 0) {
        parent.layer.msg("操作成功");
        parent.reLoad();
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        parent.layer.close(index);

      } else {
        parent.layer.alert(data.msg)
      }

    }
  });

}

function selectTeamLeader(value) {
    if(value==1){
      $("#teamUserDiv").css("visibility","");
    }else{
        $("#teamUserDiv").css("visibility","hidden");
        $("input[name='teamUserId']").attr("checked",false);
    }
}

function getCheckedUser() {
  var adIds = "";
  $("input:checkbox[name=teamUserId]:checked").each(function(i) {
    if (0 == i) {
      adIds = $(this).val();
    } else {
      adIds += ("," + $(this).val());
    }
  });
  return adIds;
}

function validateRule() {
  var icon = "<i class='fa fa-times-circle'></i> ";
  $("#signupForm").validate({
    rules : {
      name : {
        required : true
      }
    },
    messages : {
      name : {
        required : icon + "请输入名字"
      }
    }
  })
}