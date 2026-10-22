package fr.techcrud.minibank.adapter.in.web.DTO;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds
) {
    public static LoginResponse bearer(String token, long expiresInSeconds) {
        return new LoginResponse(token, "Bearer", expiresInSeconds);
    }
}
