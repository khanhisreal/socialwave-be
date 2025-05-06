package personal_project.socialwave_be.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import personal_project.socialwave_be.dto.Post.*;
import personal_project.socialwave_be.entity.Post;
import personal_project.socialwave_be.entity.User;
import personal_project.socialwave_be.repository.Post.PostRepository;
import personal_project.socialwave_be.service.Post.PostService;
import personal_project.socialwave_be.service.User.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    private PostService postService;
    private UserService userService;
    private PostRepository postRepository;
    private PostMapper postMapper;

    @Autowired
    public PostController(UserService userService, PostMapper postMapper, PostService postService, PostRepository postRepository) {
        this.userService = userService;
        this.postService = postService;
        this.postMapper = postMapper;
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public List<PostDTO> getPosts() {
        List<PostDTO> posts = postService.findAll();
        return posts;
    }

    @GetMapping("/posts/full")
    public List<PostUserDTO> getPostsWithUsersDetail() {
        List<PostDTO> postsDb = postService.findAll();

        //map
        List<PostUserDTO> posts = postMapper.toPostUserDTOs(postsDb);

        //return
        return posts;
    }

    @GetMapping("/posts/{userId}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable int userId) {
        List<PostDTO> listDTOs = postService.findPostsByUserId(userId);
        return ResponseEntity.ok(listDTOs);
    }

    @PostMapping("/posts/upload")
    public ResponseEntity<String> handleUploadPost(@ModelAttribute PostRequestDTO data) {
        //handle upload
        String imageSource = null;
        MultipartFile image = data.getFile();
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_userId_" + data.getUserId() + "_" + image.getOriginalFilename();
                Path uploadDir = Paths.get("C:/socialwave/uploads");
                Files.createDirectories(uploadDir);
                Path target = uploadDir.resolve(fileName);
                Files.copy(image.getInputStream(), target);
                imageSource = "/uploads/" + fileName;
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        //Fetch the user from DB
        User userDB = userService.findEntityById(data.getUserId());

        //Create new Post entity
        Post newPost = new Post();
        newPost.setUser(userDB);
        newPost.setCaption(data.getCaption());
        newPost.setImageSource(imageSource);
        newPost.setLikeCount(0);
        newPost.setCreateTime(LocalDateTime.now());

        //Save post to db
        postService.save(newPost);

        return ResponseEntity.ok("Post uploaded");
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<String> patchPost(@PathVariable int postId, @RequestBody CaptionUpdateRequest captionUpdateRequest) {

        Post temp;

        Optional<Post> postDb = postRepository.findById(postId);

        if(!postDb.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        temp = postDb.get();
        temp.setCaption(captionUpdateRequest.getCaption());
        temp.setUpdateTime(LocalDateTime.now());

        postService.save(temp);
        return ResponseEntity.ok("Post updated successfully");
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        postService.deleteById(postId);
        return ResponseEntity.ok("Post id - " + postId + " deleted successfully.");
    }

}
