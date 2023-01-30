package com.makalu.hrm.domain;

import com.makalu.hrm.enumconstant.MeetingType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "meeting_minutes")
@Data
@EqualsAndHashCode(callSuper = false)
public class PersistentMeetingMinutesEntity extends AbstractEntity {

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "meeting_date")
    private Date meetingDate;

    @Column(name = "minutes", columnDefinition = "text")
    private String minutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_type", length = 20)
    private MeetingType meetingType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meeting_minutes_user", joinColumns = @JoinColumn(name = "meeting_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))

    private List<PersistentUserEntity> attendedBy;

    @ManyToOne
    private PersistentUserEntity createdBy;

}
