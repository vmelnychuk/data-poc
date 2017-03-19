package io.fourfinanceit.dataprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationDetails {
    private String applicationId;
    private String industry;
    private boolean retailerBlacklisted;
}
