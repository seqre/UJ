package uj.jwzp2019.service.exceptions;

import org.springframework.web.client.RestClientException;

public class HTRestClientException extends RestClientException {
    public HTRestClientException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
