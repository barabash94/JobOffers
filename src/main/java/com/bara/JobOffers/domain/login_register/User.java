package com.bara.JobOffers.domain.login_register;

import lombok.Builder;

@Builder
record User( String id, String name, String password) {
}
