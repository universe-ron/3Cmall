package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "third_party")
public class ThirdParty {
    @Id
    @Column(name = "provider_id")
    private String providerId;
    @Column(name = "provider_name")
    private String providerName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
