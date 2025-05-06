package personal_project.socialwave_be.dto.Post;

import org.springframework.web.multipart.MultipartFile;

public class PostRequestDTO {
    private MultipartFile file;
    private String caption;
    private int userId;

    public PostRequestDTO(MultipartFile file, String caption, int userId) {
        this.file = file;
        this.caption = caption;
        this.userId = userId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
