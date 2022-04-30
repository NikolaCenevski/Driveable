package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.dto.Response.MessageResponse;
import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.dto.requests.GetPostsRequest;
import com.wp.driveable.driveableapi.dto.requests.PostRequest;
import com.wp.driveable.driveableapi.entity.Car;
import com.wp.driveable.driveableapi.entity.CarType;
import com.wp.driveable.driveableapi.entity.Post;
import com.wp.driveable.driveableapi.entity.User;
import com.wp.driveable.driveableapi.exceptions.BadRequestException;
import com.wp.driveable.driveableapi.exceptions.NotFoundException;
import com.wp.driveable.driveableapi.repository.CarTypeRepository;
import com.wp.driveable.driveableapi.repository.PostRepository;
import com.wp.driveable.driveableapi.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CarService carService;
    private final CarTypeRepository carTypeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CarService carService, CarTypeRepository carTypeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;

        this.carService = carService;
        this.carTypeRepository = carTypeRepository;
    }

    public List<PostResponse> getAllPosts() {
        //sortby here
        return postRepository.findAll().stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }

    public Page<PostResponse> getAllPosts(GetPostsRequest getPostsRequest, Pageable pageable) {
        if (getPostsRequest.getSortBy() != null) {

        }
        //sortby here
        List<Post> posts = postRepository.findAll();
        List<Post> newPosts = new ArrayList<>();

        if (getPostsRequest.getCarTypes() != null) {
            List<String> carTypes = getPostsRequest.getCarTypes();
            for (int i = 0; i < posts.size(); i++) {
                for (int j = 0; j < carTypes.size(); j++) {
                    if (posts.get(i).getCarTypes().contains(carTypeRepository.findByType(carTypes.get(i)))) {
                        newPosts.add(posts.get(i));
                        break;
                    }
                }
            }
            posts = newPosts;
        }
        if (getPostsRequest.getIsNew() != null) {
            posts = posts.stream().filter(r -> r.getIsNew() == getPostsRequest.getIsNew()).collect(Collectors.toList());
        }
        if (getPostsRequest.getManufacturer() != null) {
            posts = posts.stream().filter(r -> r.getCar().getManufacturer().equals(getPostsRequest.getManufacturer())).collect(Collectors.toList());
            if (getPostsRequest.getModel() != null) {
                posts = posts.stream().filter(r -> r.getCar().getModel().equals(getPostsRequest.getModel())).collect(Collectors.toList());
            }

        }
        if (getPostsRequest.getPriceFrom() != null) {
            posts = posts.stream().filter(r -> r.getPrice() >= getPostsRequest.getPriceFrom()).collect(Collectors.toList());
        }
        if (getPostsRequest.getPriceTo() != null) {
            posts = posts.stream().filter(r -> r.getPrice() <= getPostsRequest.getPriceTo()).collect(Collectors.toList());
        }
        if (getPostsRequest.getYearFrom() != null) {
            posts = posts.stream().filter(r -> r.getManufacturingYear() >= getPostsRequest.getYearFrom()).collect(Collectors.toList());
        }
        if (getPostsRequest.getYearTo() != null) {
            posts = posts.stream().filter(r -> r.getManufacturingYear() <= getPostsRequest.getYearTo()).collect(Collectors.toList());
        }
        if (getPostsRequest.getColor()!=null)
        {
            posts=posts.stream().filter(r->r.getColor().equals(getPostsRequest.getColor())).collect(Collectors.toList());
        }
        if (getPostsRequest.getMileageBelow()!=null)
        {
            posts=posts.stream().filter(r->r.getMileage()<=getPostsRequest.getMileageBelow()).collect(Collectors.toList());
        }
        List<PostResponse> postResponse=posts.stream().map(this::mapToPostResponse).collect(Collectors.toList());
        int start=pageable.getPageNumber()*pageable.getPageSize();
        int end = Math.min(start + pageable.getPageSize(), posts.size());
        if (start<end)
        {
            return new PageImpl<PostResponse>(postResponse.subList(start,end),pageable,postResponse.size());
        }
        return new PageImpl<>(new ArrayList<>(),pageable,postResponse.size());
    }

    public MessageResponse createPost(PostRequest postRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        Car car = carService.saveCar(postRequest.getManufacturer(), postRequest.getModel());
        post.setCar(car);
        post.setCreator(user);
        post.setTitle(postRequest.getTitle());
        post.setDate(LocalDate.now());
        post.setDescription(postRequest.getDescription());
        post.setMileage(postRequest.getMileage());
        post.setPrice(postRequest.getPrice());
        post.setColor(postRequest.getColor());
        post.setHorsepower(postRequest.getHorsepower());
        post.setCarTypes(postRequest.getCarType().stream().map(this::getCarType).collect(Collectors.toList()));
        post.setIsNew(postRequest.getIsNew());
        post.setManufacturingYear(postRequest.getManufacturingYear());
        post.setImages(postRequest.getImages().stream().map(String::getBytes).collect(Collectors.toList()));
        postRepository.save(post);

        return new MessageResponse("Post created successfully");
    }

    public CarType getCarType(String type) {
        return carTypeRepository.findByType(type);
    }

    public PostResponse mapToPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setCar(post.getCar());
        postResponse.setColor(post.getColor());
        postResponse.setMileage(post.getMileage());
        postResponse.setDate(post.getDate());
        postResponse.setDescription(post.getDescription());
        postResponse.setTitle(post.getTitle());
        postResponse.setHorsepower(post.getHorsepower());
        postResponse.setCarType(post.getCarTypes().stream().map(CarType::getType).collect(Collectors.toList()));
        postResponse.setPrice(post.getPrice());
        postResponse.setIsNew(post.getIsNew());
        postResponse.setManufacturingYear(post.getManufacturingYear());
        postResponse.setName(post.getCreator().getName());
        postResponse.setSurname(post.getCreator().getSurname());
        postResponse.setPhoneNumber(post.getCreator().getPhoneNumber());
        postResponse.setNumOfImages(post.getImages().size());
        return postResponse;
    }

    public PostResponse getPostById(long id) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        return mapToPostResponse(post);
    }

    public List<PostResponse> getAllPostsByUser() {
        return postRepository.getAllByCreator(
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getAllPostsByUser(long id) throws NotFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return postRepository.getAllByCreator(user).stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    public MessageResponse editPrice(int price, long id) throws NotFoundException, BadRequestException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        if (post.getCreator().getId().equals(user.getId())) {
            post.setPrice(price);
            postRepository.save(post);
            return new MessageResponse("Price changed");
        }

        throw new BadRequestException("No privileges to change price");
    }


}
