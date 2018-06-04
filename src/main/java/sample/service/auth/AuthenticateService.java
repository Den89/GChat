package sample.service.auth;

public interface AuthenticateService {
    boolean checkAccess(String name, String hash);
}
