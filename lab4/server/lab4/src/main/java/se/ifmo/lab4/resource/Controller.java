package se.ifmo.lab4.resource;

import java.time.LocalDateTime;
import java.util.Map;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se.ifmo.lab4.model.Element;
import se.ifmo.lab4.model.Response;
import se.ifmo.lab4.service.JwtService;
import se.ifmo.lab4.service.implementation.ElementServiceImplementation;
import se.ifmo.lab4.utils.AreaCheck;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {

    private final ElementServiceImplementation elementService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final JwtService jwtService;
    
    
    @GetMapping("/list")
    public ResponseEntity<Response> getElements(@RequestHeader (name="Authorization") String token){
        String jwt = token.substring(7);
        String login = jwtService.extractLogin(jwt);
        return ResponseEntity.ok(
            Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .data(Map.of("elements", elementService.list(login)))
                    .message("elements loaded")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .build()

        );
    }

    @PostMapping("/add")
    public ResponseEntity<Response> checkArea(@RequestBody Element element, @RequestHeader (name="Authorization") String token){
            String jwt = token.substring(7);
            String login = jwtService.extractLogin(jwt);
            element.setCreator(login);
            LocalTime currentTime = LocalTime.now();
            String curTime = currentTime.format(formatter);
            long scriptStart = System.nanoTime();
            boolean result = AreaCheck.check(element.getX(), element.getY(), element.getR());
            String scriptTime = String.format("%.2f", (double) (System.nanoTime() - scriptStart) * 0.0001);
            element.setDate(curTime);
            element.setExec(scriptTime);
            element.setResult(result);


            return ResponseEntity.ok(
            Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .data(Map.of("element saved", elementService.create(element)))
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .build()
            );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> clearTable(Element element, @RequestHeader (name="Authorization") String token){;
        String jwt = token.substring(7);
        String login = jwtService.extractLogin(jwt);
        log.info(login);
        elementService.clearTable(login);
        return ResponseEntity.ok(
            Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("cleared")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .build()
            );
    }
    
}
