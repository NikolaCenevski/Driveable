package com.wp.driveable.driveableapi.controller;

import com.wp.driveable.driveableapi.dto.Response.JwtResponse;
import com.wp.driveable.driveableapi.dto.Response.MessageResponse;
import com.wp.driveable.driveableapi.dto.requests.AuthRequest;
import com.wp.driveable.driveableapi.dto.requests.RegisterRequest;
import com.wp.driveable.driveableapi.entity.Post;
import com.wp.driveable.driveableapi.entity.User;
import com.wp.driveable.driveableapi.exceptions.BadRequestException;
import com.wp.driveable.driveableapi.repository.PostRepository;
import com.wp.driveable.driveableapi.service.UserService;
import com.wp.driveable.driveableapi.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PostRepository postRepository;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserService userService, PostRepository postRepository) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @GetMapping("/test")
    public void test() {
        Post post = postRepository.getById(1L);

        post.getImages().add(("iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAABGdBTUEAALGPC/xhBQAAACBjSFJN" +
                "AAB6JQAAgIMAAPn/AACA6QAAdTAAAOpgAAA6mAAAF2+SX8VGAAAABmJLR0QA/wD/AP+gvaeTAAAA" +
                "CXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH5gQBDCYLHOMwUgAAEWJJREFUaN61mnt0XVWdxz+/" +
                "fR735t68mqSkSUtb+kjTtDVWKNACrWhFUHytUbTi6DDge0BEnQWOLhBHZ/7ApTNlwThIGcYXI4oi" +
                "8igVKZVSLBRsoW3SF23TtCXNq8lN7rn3nrN/88e5eael6Mxe667knrMfv+/v+d17X+GvaNEzFYgR" +
                "VDUFzAfeVvwsBKYDlUAKtUjZskCaftJHoq4djVpBt6HRi3p0favMeP9AeIfgfe0vl0X+kkF2Szn4" +
                "6uiAmQ9cDrwbeAswFfAmH2WQs7+CzLoZxAEIgU49fv923XP9E4h5AtgLRM7Kk/+/QPo+14ykznJt" +
                "x+5lydWDn3LnRO8tav4M5lFwypGGO5GaD4w8Dg6ju9aoZna0I87vgPuAl4DwzQA6IyAadEGiCrDz" +
                "9eSJG4L7v73Gdu2sdqpb8BcH4CvomUxkkVQjsvB+SC8cefz6z9G9XwLNA5wAfgqsBQ4AnAkg5w3X" +
                "DgdBxEecjyHmbkmWvkeSqZTbcDG2u4qwtQW3Ln8GMwEiUOiAwuvIlNVgkvHjkjkwuDv+iEkDFwKr" +
                "gS5gz63XJO3t9+VOO7U51Yv+6+ehaiHMVeGWfgdxfwjSCGCq6tDcIIlPfAOpu4b89pIzswiAOGjX" +
                "Y2j7f4DaojrTyIzrwa9l1ESLgHuA24HKaFMFdlPFm7OIZvZh5qxEM13Tw+3PrtXOY9dqMJAQxyXa" +
                "9zK5X99J+MIGnBnz8C64ksKftiPuQUz5qdAoaAQUBRcg8wqkG5HU/PhZYjoUeqFvC6M8PgEsB2YC" +
                "W4DMbdck+dYk1pkQI/ryY9D8LlA7A5y7NDf4Pu3vQbuOEv55I/kND6CZXhRwamdScsMPsEf2E275" +
                "KomLT46bUeMlpA6Z8rY4Ww22oNm9oAUkvSSOl1RD3D1oQ3d9DM3sGMpso9tvgC8AxxRwx8XNhN63" +
                "3X0f2LAa46/FOB8SL4GkKzBTZ4BA4dmHwVrEGGx/L9p1BFM7k2hfC25dF5IYBcKUIHWfJzx6IWbB" +
                "jZhZn0KqrwSnFDLbIdcGhU5kyjvBJMCtAFMCPU8B0XjRGoF64A8CwfiYGRMj0aGXyf3sW4nC1g3f" +
                "wNoPT/DDmQsx02ahNgKNlW9mLsR/5xpM3VLsuOQi0z6JzPo66BQohLF1EvXIzK8hddcBDtr5CHr0" +
                "P4fjRWo+gFS9q+iKE9pHga8DfjQuXoaBaL4Xmf5WEh/5+scJ85/RIDPB7aSkDGdWI6Z8CjgOpMtw" +
                "l14Kro+UTUVzgkjRGt5ZyLRPopGgUYiUlI4o7GArhdYUmKmgIdp+J/RsKGorjUyfEPij5f08cBXA" +
                "aDAGQI88Cm4akdwi/NQtUl6T0q6j44LHEu58Fm/5laRvfYDUl+/Ef9fHMbWz4vc2GokPtYhfB4kZ" +
                "RLufR9IVSEV13K2tlfCl3+MsWI2UzY2FLXShB2+H7L54fPn5SO3Vp8qEaeCfiq42ziK1qyDMeIh7" +
                "IzDf1M8lem0nGgwMdwx3bYEoxG1ehTl7Ae55l+FdcAV64jBEIXqyHZNSVAEEtTnClzYQHWrBO3c1" +
                "iIGwQPjqZtxzV+PMagBb9HNx0MwO9OB3IeoHMUjdtUjp4pEUPTFevgS4Q1YxJ9csADeN7Th+CSpx" +
                "XIQFco/ey+C/XkNw/+3kHr4LPdmJ23wpmJH8YKrr0Uwv9vhh9GQLUlGs8GJg8DVE2vDfsQapnBob" +
                "qr8bLeQw9fMh8yoavBb3HQLT+TB69EexlZIzof6GOPgnb1cBK4ZczE1cdJKBL4pHzXuv9S//bKXT" +
                "cC75P/wP9lArqkq4ayuSSJL6x3vAHccHk6VER/ZSeOkZTEUbUjLarQNMyTOIfxUQa03DEHESSNSB" +
                "tn0fCj0jQGINokfWImVLsWEj+Sd2YiIPd0FuMjerAq4lri8F11sU4C+tWJx77oXVhadKwYZEe19C" +
                "VRGnqP18Htu2B5ZcMmamaO828k/8DHGOUXJlMHYZMWjPRmi5Fuo/g5Q2I04Gk2pBWx5EM1vGgYhd" +
                "krATu+sWgt9XUXh1N2aKh6lxMdXhZGAuBxYDLzu3XpsA+LRTY6+MjvQj1efjr/oQ0Wuvoj0nUGsR" +
                "z0PKK3FqZ8Z8yUZEr24mWHc7mjlCYmWAU1eYZCGBoA26H0dPPIR0/wrhOcgdmATEqDG2A9t1Anss" +
                "AVmD5gR3VmEyQpUG2oFNEm2qqAR+i3CJ9gu5F2sw867FXbic/FM/xVTX4y65CFM/F9t9HAb7sMcP" +
                "kvvtOgg7SKwIcBsD3rjpiKBv1AQ0MARPlhId8sFVEisH8BYFk1nlj8D7XaAJWIiClCmJ5Z0Udv4b" +
                "+f1PQ2o2bvOlmGmzwXGQdBW2u4No7wuY6tfxlw7i1BcmkdkWBR8tvFAsMmeEWZIWf1mWoMtFM4b8" +
                "SyU4tSGmZoKLLQQWSbSp4svAHYwznO0VojYf21OG2nLABbKYZA9OXRan3o7bh2gMwJQUa0hdTDmQ" +
                "OKXmO9DcMbCZGNcZsuX8yyXkt6TACm5DjuQ7MuCOGWyBr7rAueNBIGCmKKYqB1EOws54YaeIZ8hT" +
                "hubTCLypSNXlSM37IL0YvCowfvF9CFEGbXuW/CM349T149QDtn8ycjhGDq8pIGr3iA76hPt9CnUe" +
                "3pLcaBc1wLkusGCMVXOCZhxMVRh3EcbuwscoQwGDVL0bOfsrUL4MxJ1EIC+21LTVRJ1vIdx7iNQN" +
                "X0AKj6LdTzFM7yd1MSWxLEv2hIPmkqj3QUhsh+DV4loCsMAQ77mHNRDuS5P9TZrg6TS2wz1NbCpI" +
                "Apn+D0jjPVCxfHIQo/GkynCbV2C7uolOVCEL1yFnXUWRDpxyGVObx78oQfLvv0niU2sxc7+JlF8I" +
                "TtlQr+mGoWqFIn4d7oo7cM/7CNGhWsJDDuF+H804EwGZZHwqMvsb4E6ZXAhbAI3Qzt1oVytgcZsu" +
                "AM8n3L4RpAwq336aVDzSvIWD+EtrED8BVVcgSx5Bpl41xJIrXCA5DF4tpjZHyXVfJTq8hqjlp+Qe" +
                "X4/f1IW3dBStthFqV2DqvxjvI8YrMdsFUQHta0P7DkOYxb62Aalpwsy6AmdmI1HLNmxXOzKwPY6h" +
                "08UKAmE32v0kUnVZ3Dc4GO/xYw0n3TGd88fRPV+Ged/DabgGZ04ztrMC7b17VDclavfI7+qjZHE/" +
                "proUojzYAprtQnsOgJvAtv4GSqqwr23AzFgBNsSZewVy1hLcxReS++2PiHb8ArfiSSb139GPikQU" +
                "k4BCD9r5MBz5wRiu5gIBkBoZbUdM7bo458wg3GwgL+BZxK3CDjYTHdhNtP/PmOo6NHOUqOUhZMpc" +
                "ou3rMNUN2I5XcBZ/AnP2JTiLP4EOnoDymYDgLL4IWf/f6ME7YUH3WNcSIC/YfgcNBHEVKbNIicLr" +
                "P0Z7nkaz+8AGo8cFLnByBAhxLTj+Y0icDRoi4U+w/Q62z4n5TtU7cZatgY1fJNqxGbepmWjPw9h9" +
                "v8PMXImUTcfMfS/m7FXI1CaY+x7EK0GmzB1ewpnVhH9JPc7M51E7CkQkhAd8CruS2G4HLQgYxZRZ" +
                "3Dl5vKZ+JP0K4IyPq5MucBSoG9GIQftfQHddDaJIlEPSpWggYDyovAyn/jzM9DmEu/6E3/thzPQL" +
                "EC+NTF2EpOvATSKltUVKP9FtJNqF19QOhVHvQiG3NUVhRxJCKbpWnN5tYMh3ukRHPRKrMpgp0fiC" +
                "etQArZM6qM2CzWL7XSRpYxMXHVdKK3AXLcceP4h9vRNTfyHO4qsxtW9FKmcjpdM4JSXp/SO670bI" +
                "t40JhMLOJIXtSYhiKyDFQwHR+DtK1OaRey4NuQnzthpgG6eqSFawPQ7iK5JS1IZw+A7oWY+75AIQ" +
                "h3DHH4vxVBJ/TtVsFj3+Y7T1s+hg66gNFWifQ/6VIggpHiGNTwDFR9Ehj/CgP1pHFtjmAluBHqB6" +
                "glF6XMhJbJG0BQQdbEFbrsO4y3AbIDr4PNp3AimfOjmAaAD6t6HH1qHdj0OUnRDc0TEP7TNFEEMu" +
                "NV7rMUiNhPCwjzs/N9SlB9jqAjuB3cDFY8blheioC75iKiNwRm1jwz4IN5Bc6WAHetGDX4PqZeBP" +
                "R9wyUIuG3TC4B/q2opk/Q9gb5//xxU/Bdjtgiy41+sX4jkXntn0mjiNPKcq+0y0i2jAGiBKbL23R" +
                "AYMzgToL4IALpjKAk79Cex8C8VBx4gk0jD8UwYsz4jE6Vk7NT26DsesNDZLx/TYAPUPqeZT4OD82" +
                "9VEPTLy+KbeQOD0XInKJB0RAHijEL8QpVux4advjEO5JYLvGcjIpTyLGgLWnoPc65j8pt0NU/kRR" +
                "9mH6vgNYj4D2G2yPizOtgO1zcKYVTr13ENBBQ/B0KdExL674L6bQk86EfrbLJXi8nODJMrKPlREd" +
                "94qKjvCXN5P8u1sws5uKNflUbDgukO7s/JDk64uyDwMpAPei9ETtPs70Apo1cYD7p7FGJOS3pbCd" +
                "DjpoyD1dSu65FMEfStGsGfYVEYjaPWxX7Ha2xyE67I0Ip3vwV64gffN/kbz6ZsyM+TEjHg9IwZ1d" +
                "wJ2VB6UHuLcoO2boNsg0Bptth/dLFExViOYF8e2pQQiEexKE+3285iB2mR4HEdCsjEnoqmAqIvDj" +
                "rCOexkUN4gIcHEaP3IVUlOFf+WlSt6wj8dGbMHWzwWrxIzgzCvjLB4aU+yCwGeIbLQGInplC9rEy" +
                "TMou8pYO/tpURfNtp4vtduI0pxNB2A6X7PoyvHn5mG9uS8b9PCX59oGJZ1FWCPf5hEc8nGkhXkNu" +
                "7JZVfKTxR0jN+0eGdByhsPFBCs/9HKemHf/cACmLQNkDfBDYrQLuJSfjjauzqge7q4Dd6++UKdF3" +
                "sNxlKqJUdNzFdriYs8ZmLQ0MuS1pzJQIqYjIb04Np2bvLeDOy4KOS7NGcRfkcBtyE/fsapHENKRk" +
                "Xvy95ym00IMR8M/vx60NEDLgCCiDwHeJ0y7uJbFHDacP05SJT7eVB4BmHP2SOydvwgM+tt/BnZEf" +
                "yV4FMOURzowC+RdL0MAAFmfxChIf/1ukey3au3HEfKN8fMzfYWu4UP8ZSDfB4F50741org1FYuJq" +
                "intuxQI/BB4Ycqlh+UfPV3yRA74D/FJKLF5jDklaCq1JbG+cjaTU4q8YIDzgYzvj7bCUWRKXnoWp" +
                "uxBpvDe+GjDpyQ+hRzMQjZCyZUjtmvhr58NocHgE8aj0DfyyKFtu/E2vmbiGQHybehPKIxjFmV7A" +
                "PSdHdNhHB4rZKDAxtVDAUfxlAYZ1aMvnoNCFnHMb0rAWKZk/9tImEsLWBOG+RMytUPCnglsOuSNw" +
                "4iFOke8fAW4CumSS0jlhf/mt+wJuvaYEoB/YRHw40SRJFaygfQ6mMkI8RZKKZgxuQx6/OQCx8faz" +
                "dxP40+Lbp8qVkD8O2f2AUmhJkttYSnggPkF06i3k22GwBToeRDPbxrNmC/wCuAFoFwWzauK9+ylZ" +
                "wajboCrgFoTP2y43bXsc3HkjGUkDg3gac7HhWLDgliN11yEzbgBx0WN3E227h+zvIjTjoAp+c0Bi" +
                "ZSbOzxrF4ozduw8AdwP/AnQLglnZO6m8b3iGGW2qQARflY8UWpLfdKaGCyY5tpykFXlR5aXIObeh" +
                "hakMfv86otbdgIOpCUmsGETSNt55TmwtwD8T14v8G/364YwOY7Pfqyd501Fy99Q2eAuC6xHWMJ72" +
                "nxJPBIm55F9eTP6ZrXFwJxT/4kHC/T6SsiTfnhktSRfwc+DfDbLXomf0E4439aOaoru5wDLiS5b3" +
                "ANN4A+IatvoET6cgjBmwf24WVCjs9yl5dz9maqgox4HHgHXEe6T/+x/VnAZQI3AFcBnxhUsNo2oT" +
                "ArbTJft42TCRdOfkcWblyW9Nhf75g13eouAVlCeBx4nd6U0B+KuAjAUkgJYCDcB5xIfijcB0MVTm" +
                "t5ckc5vSBtG8JLQn8Y6BtrAlsS963X0x9TcnnzfV4R5JaCbqNxN+zfBm2v8CiY1u6bwIUEIAAAAl" +
                "dEVYdGRhdGU6Y3JlYXRlADIwMjItMDQtMDFUMTI6Mzg6MTArMDA6MDDeAEFXAAAAJXRFWHRkYXRl" +
                "Om1vZGlmeQAyMDIyLTA0LTAxVDEyOjM4OjEwKzAwOjAwr1356wAAAABJRU5ErkJggg==").toString().getBytes());

        postRepository.save(post);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody AuthRequest authRequest) {
        User user = (User) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
        ).getPrincipal();

        String token = jwtUtil.generateToken(authRequest.getUserName());
        return ResponseEntity
                .ok(new JwtResponse(
                        token,
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getUserRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(userService.addUser(registerRequest));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
