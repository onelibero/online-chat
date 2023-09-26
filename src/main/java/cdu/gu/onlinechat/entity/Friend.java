package cdu.gu.onlinechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private int id;
    private String userid;
    private String friend_id;
    private Byte status;
}
