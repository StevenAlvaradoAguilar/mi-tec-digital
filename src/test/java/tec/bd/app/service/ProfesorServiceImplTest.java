package tec.bd.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tec.bd.app.dao.ProfesorDAO;
import tec.bd.app.domain.Estudiante;
import tec.bd.app.domain.Profesor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProfesorServiceImplTest {

    @Mock
    private ProfesorDAO profesorDAO;

    @InjectMocks
    private ProfesorServiceImpl profesorService;


    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
    public void whenNoDataInDB_thenNoResult() throws Exception {

        given(this.profesorDAO.findAll()).willReturn(Collections.emptyList());

        var estudiantes = this.profesorService.getAll();

        verify(this.profesorDAO, times(1)).findAll();

        assertThat(estudiantes).hasSize(0);
    }

    @Test
    public void getAllTest() throws Exception {

        given(this.profesorDAO.findAll()).willReturn(List.of(
                mock(Profesor.class), mock(Profesor.class), mock(Profesor.class)
        ));

        var profesores = this.profesorService.getAll();

        verify(this.profesorDAO, times(1)).findAll();

        assertThat(profesores).hasSize(3);

    }

    @Test
    public void addNewStudent() throws Exception {

        /*
        En la primera invocacion va a devolver una lista de 3 estudiantes. En la segunda una lista de 4
         */
        given(this.profesorDAO.findAll()).willReturn(
                List.of(mock(Profesor.class), mock(Profesor.class), mock(Profesor.class)),
                List.of(mock(Profesor.class), mock(Profesor.class), mock(Profesor.class), mock(Profesor.class))
        );

        var teachersBeforeSave = this.profesorService.getAll();

        var lorena = new Profesor(2, "Lorena", "Lopez", "San Jose");
        profesorService.addNew(lorena);

        var teachersAfterSave = this.profesorService.getAll();

        verify(this.profesorDAO, times(1)).save(lorena);
        assertThat(teachersAfterSave.size()).isGreaterThan(teachersBeforeSave.size());
    }

    @Test
    public void deleteTeacher() throws Exception {

        /*
        En la primera invocacion va a devolver una lista de 3 estudiantes. En la segunda una lista de 2
         */
        given(this.profesorDAO.findAll()).willReturn(
                List.of(mock(Profesor.class), mock(Profesor.class), mock(Profesor.class)),
                List.of(mock(Profesor.class), mock(Profesor.class))
        );

        //Dado que profesorDAO encuentre cualquier entero debe retornar el valor del mock de la clase profesor
        given(this.profesorDAO.findById(anyInt())).willReturn(Optional.of(mock(Profesor.class)));

        var teachersBeforeSave = this.profesorService.getAll();

        profesorService.deleteTeacher(2);

        var teachersAfterSave = this.profesorService.getAll();

        verify(this.profesorDAO, times(1)).delete(2);

        assertThat(teachersAfterSave.size()).isLessThan(teachersBeforeSave.size());
    }

    @Test
    public void updateTeacher() throws Exception {

        /*
        En la primera invocacion va a devolver estudiante default y en la segunda invocacion el estudiante actualizado
         */
        given(this.profesorDAO.findById(anyInt())).willReturn(
                Optional.of(mock(Profesor.class)),
                Optional.of(mock(Profesor.class))
        );

        var teacherBefore = this.profesorService.getById(2);

        var pedro = new Profesor(2, "Pedro", "Lopez", "San Jose");
        profesorService.updateTeacher(pedro);

        var teacherAfter = this.profesorService.getById(2);

        verify(this.profesorDAO, times(1)).update(pedro);

        assertThat(teacherAfter).isNotSameAs(teacherBefore);
    }

    @Test
    public void getTeachersByLastName() throws Exception {
        //TODO: hay que implementarlo
        List<Profesor> profesores = Arrays.asList(mock(Profesor.class), mock(Profesor.class));

        given(profesorDAO.findByLastName(anyString())).willReturn(profesores);

        var actual = this.profesorService.getTeachersByLastName("Lorena");

        verify(profesorDAO, times(1)).findByLastName("Lorena");

        assertThat(actual).hasSize(2);

    }

}
