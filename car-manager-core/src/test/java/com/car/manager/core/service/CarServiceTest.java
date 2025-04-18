package com.car.manager.core.service;

import com.car.manager.core.domain.Car;
import com.car.manager.core.dto.car.CarDTO;
import com.car.manager.core.dto.car.CarResponseDTO;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.UniqueValueException;
import com.car.manager.core.gateway.CarGateway;
import com.car.manager.core.mapper.CarDTOMapper;
import com.car.manager.core.mapper.CarDTOMapperImpl;
import com.car.manager.core.storage.FileStorage;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    private static String LOGIN = "2";

    @Mock
    private CarGateway gateway;

    @Mock
    private AvatarService avatarService;

    private CarDTOMapper mapper;

    private CarService subject;


    @BeforeEach
    public void setup(){
        this.mapper = new CarDTOMapperImpl();
        this.subject = new CarService(gateway, this.mapper, avatarService);
    }

    @Test
    public void create_whenItValid(){
        Car car = mockCarInstance();
        when(gateway.save(any(Car.class))).thenReturn(car);
        when(gateway.existsByLicensePlate(eq(car.getLicensePlate()))).thenReturn(false);
        CarDTO request = mapper.toDto(car);

        CarDTO response = subject.create(request, LOGIN);

        assertInstances(request, response);
    }

    @Test
    public void notCreate_whenCarExists(){
        Car car = mockCarInstance();
        when(gateway.existsByLicensePlate(eq(car.getLicensePlate()))).thenReturn(true);
        CarDTO request = mapper.toDto(car);

        assertThrows(UniqueValueException.class, () -> subject.create(request, LOGIN));
    }

    @Test
    public void update_whenItValid(){
        Car car = mockCarInstance();
        when(gateway.save(any(Car.class))).thenReturn(car);
        when(gateway.findById(eq(car.getId()), eq(LOGIN))).thenReturn(Optional.of(car));
        CarDTO request = mapper.toDto(car);

        CarDTO response = subject.update(car.getId(), request, LOGIN);

        assertInstances(request, response);

    }

    @Test
    public void doNotUpdate_whenNotFound(){
        Car car = mockCarInstance();
        when(gateway.findById(eq(car.getId()), eq(LOGIN))).thenReturn(Optional.empty());
        CarDTO request = mapper.toDto(car);

        assertThrows(InstanceNotFoundException.class, () -> subject.update(car.getId(), request, LOGIN));
    }

    @Test
    public void update_whenLicensePlateIsEqualToRequest(){
        Car car = mockCarInstance();
        when(gateway.findById(eq(car.getId()), eq(LOGIN))).thenReturn(Optional.of(car));
        CarDTO request = mapper.toDto(car);
        request.setLicensePlate("ABC1234");
        when(gateway.existsByLicensePlate(eq(request.getLicensePlate()))).thenReturn(true);

        assertThrows(UniqueValueException.class, () -> subject.update(car.getId(), request, LOGIN));
    }

    @Test
    public void read_WhenExists(){
        Car car = mockCarInstance();
        when(gateway.findById(eq(car.getId()), eq(LOGIN))).thenReturn(Optional.of(car));
        CarResponseDTO responseDTO = subject.read(car.getId(), LOGIN);

        assertInstances(car, responseDTO);
    }

    @Test
    public void notRead_WhenNotFound(){
        Car car = mockCarInstance();
        when(gateway.findById(eq(car.getId()), eq(LOGIN))).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> subject.read(car.getId(), LOGIN));
    }

    @Test
    public void delete_WhenExists(){
        Car car = mockCarInstance();
        when(gateway.findById(eq(car.getId()), eq(LOGIN))).thenReturn(Optional.of(car));
        subject.delete(car.getId(), LOGIN);

        verify(gateway).delete(eq(car.getId()));
    }

    @Test
    public void notDelete_WhenNotFound(){
        Car car = mockCarInstance();
        when(gateway.findById(eq(car.getId()), eq(LOGIN))).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> subject.delete(car.getId(), LOGIN));
    }

    private Car mockCarInstance(){
        return Instancio.of(Car.class)
                .generate(field(Car::getLicensePlate), gen -> gen.string().maxLength(7))
                .generate(field(Car::getColor), gen -> gen.string().maxLength(20))
                .generate(field(Car::getModel), gen -> gen.string().maxLength(20))
                .create();
    }

    private void assertInstances(CarDTO request, CarDTO response){
        assertEquals(request.getLicensePlate(), response.getLicensePlate());
        assertEquals(request.getModel(), response.getModel());
        assertEquals(request.getColor(), response.getColor());
        assertEquals(request.getYear(), response.getYear());
    }

    private void assertInstances(Car car, CarDTO response){
        assertEquals(car.getLicensePlate(), response.getLicensePlate());
        assertEquals(car.getModel(), response.getModel());
        assertEquals(car.getColor(), response.getColor());
        assertEquals(car.getYear(), response.getYear());
    }
}
