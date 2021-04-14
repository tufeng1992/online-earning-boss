package com.powerboot.system.request;

import java.util.List;

public class TeamLeaderUpdateRequest {

    private Long userId;
    // 用户名
    private String username;
    //是否团队长 0-不是 1-是
    private Integer teamLeader;
    //团员
    private List<Long> teamUserIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(Integer teamLeader) {
        this.teamLeader = teamLeader;
    }

    public List<Long> getTeamUserIds() {
        return teamUserIds;
    }

    public void setTeamUserIds(List<Long> teamUserIds) {
        this.teamUserIds = teamUserIds;
    }
}
