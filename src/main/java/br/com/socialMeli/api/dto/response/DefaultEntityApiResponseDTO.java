package br.com.socialMeli.api.dto.response;

public abstract class DefaultEntityApiResponseDTO {

    private boolean success;

    private String description;

    private Long id;

    public DefaultEntityApiResponseDTO(boolean success, String description, Long id) {
        this.success = success;
        this.description = description;
        this.id = id;
    }

    public DefaultEntityApiResponseDTO(boolean success, String description) {
        this.success = success;
        this.description = description;
    }
}
