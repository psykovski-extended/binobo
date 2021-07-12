package htlstp.diplomarbeit.binobo.model;

import java.util.UUID;

public class DataAccessToken {

    private Long id;
    private String token = UUID.randomUUID().toString();

}
