package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.dto.requests.PostRequest;
import com.wp.driveable.driveableapi.entity.Car;
import com.wp.driveable.driveableapi.entity.Post;
import com.wp.driveable.driveableapi.entity.User;
import com.wp.driveable.driveableapi.repository.CarRepository;
import com.wp.driveable.driveableapi.repository.PostRepository;
import com.wp.driveable.driveableapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CarService carService;
    public PostService(PostRepository postRepository, CarRepository carRepository, UserRepository userRepository, CarService carService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;

        this.carService = carService;
    }
    public List<PostResponse> getAllPosts()
    {
        return postRepository.findAll().stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }
    public void createPost(PostRequest postRequest)
    {
       User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Post post=new Post();
       Car car=carService.saveCar(postRequest.getManufacturer(),postRequest.getModel());
       post.setCar(car);
       post.setCreator(user);
       post.setTitle(postRequest.getTitle());
       post.setDescription(postRequest.getDescription());
       post.setPrice(postRequest.getPrice());
       post.setColor(postRequest.getColor());
       post.setHorsepower(postRequest.getHorsepower());
       post.setCarType(postRequest.getCarType());
       post.setIsNew(postRequest.getIsNew());
       post.setManufacturingYear(postRequest.getManufacturingYear());
       post.setImages(postRequest.getImages());
       postRepository.save(post);


    }

    public PostResponse mapToPostResponse(Post post)
    {
        PostResponse postResponse= new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setCar(post.getCar());
        postResponse.setColor(post.getColor());
        postResponse.setMileage(post.getMileage());
        postResponse.setDate(post.getDate());
        postResponse.setDescription(post.getDescription());
        postResponse.setTitle(post.getTitle());
        postResponse.setHorsepower(post.getHorsepower());
        postResponse.setCarType(post.getCarType());
        postResponse.setPrice(post.getPrice());
        postResponse.setIsNew(post.getIsNew());
        postResponse.setManufacturingYear(post.getManufacturingYear());
        postResponse.setName(post.getCreator().getName());
        postResponse.setSurname(post.getCreator().getSurname());
        postResponse.setPhoneNumber(post.getCreator().getPhoneNumber());
        postResponse.setNumOfImages(post.getImages().size());
        return postResponse;
    }

    public PostResponse getPostById(long id) {
        Post post =postRepository.findById(id).orElse(null);
        if (post!=null)
        {
            return mapToPostResponse(post);
        }
        return null;
    }

    public List<PostResponse> getAllPostsByUser() {
       return postRepository.getAllByCreator((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }
    public List<PostResponse> getAllPostsByUser(long id)
    {
        return postRepository.getAllByCreator(userRepository.getById(id)).stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }
    public void editPrice(int price,long id) {
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post=postRepository.getById(id);
        if (post.getCreator().getId().equals(user.getId()))
        {
            post.setPrice(price);
            postRepository.save(post);
        }
    }
}
