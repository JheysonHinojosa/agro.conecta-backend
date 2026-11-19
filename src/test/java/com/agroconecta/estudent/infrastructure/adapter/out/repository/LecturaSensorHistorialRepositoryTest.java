package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.application.dto.response.SensorHistorialResponse;
import com.agroconecta.estudent.application.services.impl.SensorHistorialServiceImpl;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CentroAcopioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LecturaSensorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.SensorIotEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:sensor-history;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LecturaSensorHistorialRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LecturaSensorRepository lecturaSensorRepository;

    private SensorHistorialServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new SensorHistorialServiceImpl(lecturaSensorRepository);
    }

    @Test
    void historial_filtraCentroYFechasOrdenaEmpatesPaginaYCuentaEnBaseDeDatos() {
        CentroAcopioEntity centroUno = crearCentro("CENTRO-01");
        CentroAcopioEntity centroDos = crearCentro("CENTRO-02");
        SensorIotEntity sensorA = crearSensor(centroUno, "SENSOR-A");
        SensorIotEntity sensorB = crearSensor(centroUno, null);
        SensorIotEntity sensorOtroCentro = crearSensor(centroDos, "SENSOR-X");

        crearLectura(sensorA, "2026-03-01T00:00:00Z", "10.00", "50.00");
        crearLectura(sensorA, "2026-03-02T12:00:00Z", "20.00", "60.00");
        crearLectura(sensorB, "2026-03-02T12:00:00Z", null, null);
        crearLectura(sensorA, "2026-03-02T23:59:59Z", "30.00", "70.00");
        crearLectura(sensorOtroCentro, "2026-03-02T12:00:00Z", "40.00", "80.00");
        crearLectura(sensorA, "2026-03-03T00:00:00Z", "50.00", "90.00");
        entityManager.flush();
        entityManager.clear();

        SensorHistorialResponse firstPage = service.consultar(
                centroUno.getIdCentroAcopio(),
                LocalDate.parse("2026-03-01"),
                LocalDate.parse("2026-03-02"),
                1,
                2
        );
        assertEquals(4, firstPage.total());
        assertEquals(2, firstPage.items().size());
        assertEquals(OffsetDateTime.parse("2026-03-02T23:59:59Z"), firstPage.items().get(0).fecha_lectura());
        assertEquals(OffsetDateTime.parse("2026-03-02T12:00:00Z"), firstPage.items().get(1).fecha_lectura());
        assertNull(firstPage.items().get(1).codigo_sensor(),
                "La segunda lectura insertada a la misma hora debe aparecer primero por id descendente.");
        assertNull(firstPage.items().get(1).temperatura_c());
        assertNull(firstPage.items().get(1).humedad_pct());

        SensorHistorialResponse secondPage = service.consultar(
                centroUno.getIdCentroAcopio(),
                LocalDate.parse("2026-03-01"),
                LocalDate.parse("2026-03-02"),
                2,
                2
        );
        assertEquals(4, secondPage.total());
        assertEquals(List.of(
                OffsetDateTime.parse("2026-03-02T12:00:00Z"),
                OffsetDateTime.parse("2026-03-01T00:00:00Z")
        ), secondPage.items().stream().map(SensorHistorialResponse.Item::fecha_lectura).toList());

        SensorHistorialResponse outOfRangePage = service.consultar(
                centroUno.getIdCentroAcopio(),
                LocalDate.parse("2026-03-01"),
                LocalDate.parse("2026-03-02"),
                3,
                2
        );
        assertEquals(4, outOfRangePage.total());
        assertEquals(List.of(), outOfRangePage.items());
    }

    @Test
    void historial_sinCoincidenciasDevuelveItemsVaciosYTotalCero() {
        CentroAcopioEntity centro = crearCentro("CENTRO-VACIO");
        entityManager.flush();

        SensorHistorialResponse response = service.consultar(
                centro.getIdCentroAcopio(),
                LocalDate.parse("2026-03-01"),
                LocalDate.parse("2026-03-02"),
                1,
                25
        );

        assertEquals(List.of(), response.items());
        assertEquals(0, response.total());
    }

    private CentroAcopioEntity crearCentro(String codigo) {
        CentroAcopioEntity centro = new CentroAcopioEntity();
        centro.setCodigo(codigo);
        centro.setNombre(codigo);
        centro.setActivo(true);
        entityManager.persist(centro);
        return centro;
    }

    private SensorIotEntity crearSensor(CentroAcopioEntity centro, String codigo) {
        SensorIotEntity sensor = new SensorIotEntity();
        sensor.setCodigoSensor(codigo);
        sensor.setCentroAcopio(centro);
        sensor.setActivo(true);
        entityManager.persist(sensor);
        return sensor;
    }

    private LecturaSensorEntity crearLectura(
            SensorIotEntity sensor,
            String fecha,
            String temperatura,
            String humedad
    ) {
        LecturaSensorEntity lectura = new LecturaSensorEntity();
        lectura.setSensor(sensor);
        lectura.setFechaLectura(OffsetDateTime.parse(fecha));
        lectura.setTemperaturaC(temperatura == null ? null : new BigDecimal(temperatura));
        lectura.setHumedadPct(humedad == null ? null : new BigDecimal(humedad));
        entityManager.persist(lectura);
        return lectura;
    }
}
