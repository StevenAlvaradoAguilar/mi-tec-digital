package tec.bd.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tec.bd.app.dao.CursoDAO;

import tec.bd.app.domain.Curso;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CursoServiceImplTest {

    @Mock
    private CursoDAO cursoDAO;

    @InjectMocks
    private CursoServiceImpl cursoService;


    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
    public void whenNoDataInDB_thenNoResult() throws Exception {

        given(this.cursoDAO.findAll()).willReturn(Collections.emptyList());

        var cursos = this.cursoService.getAll();

        verify(this.cursoDAO, times(1)).findAll();

        assertThat(cursos).hasSize(0);
    }

    @Test
    public void getAllTest() throws Exception {

        given(this.cursoDAO.findAll()).willReturn(List.of(
                mock(Curso.class), mock(Curso.class), mock(Curso.class)
        ));

        var cursos = this.cursoService.getAll();

        verify(this.cursoDAO, times(1)).findAll();

        assertThat(cursos).hasSize(3);

    }

    @Test
    public void addNewStudent() throws Exception {

        /*
        En la primera invocacion va a devolver una lista de 3 estudiantes. En la segunda una lista de 4
         */
        given(this.cursoDAO.findAll()).willReturn(
                List.of(mock(Curso.class), mock(Curso.class), mock(Curso.class)),
                List.of(mock(Curso.class), mock(Curso.class), mock(Curso.class), mock(Curso.class))
        );

        var studentsBeforeSave = this.cursoService.getAll();

        var karol = new Curso(2, "Karol", 5, "");
        cursoService.addNew(karol);

        var studentsAfterSave = this.cursoService.getAll();

        verify(this.cursoDAO, times(1)).save(karol);
        assertThat(studentsAfterSave.size()).isGreaterThan(studentsBeforeSave.size());
    }

    @Test
    public void deleteCourse() throws Exception {

        /*
        En la primera invocacion va a devolver una lista de 3 estudiantes. En la segunda una lista de 2
         */
        given(this.cursoDAO.findAll()).willReturn(
                List.of(mock(Curso.class), mock(Curso.class), mock(Curso.class)),
                List.of(mock(Curso.class), mock(Curso.class))
        );

        given(this.cursoDAO.findById(anyInt())).willReturn(Optional.of(mock(Curso.class)));

        var coursesBeforeSave = this.cursoService.getAll();

        cursoService.deleteCourse(1);

        var coursesAfterSave = this.cursoService.getAll();

        verify(this.cursoDAO, times(1)).delete(1);

        assertThat(coursesAfterSave.size()).isLessThan(coursesBeforeSave.size());
    }

    @Test
    public void updateCourse() throws Exception {
        /*
        En la primera invocacion va a devolver estudiante default y en la segunda invocacion el estudiante actualizado
         */
        given(this.cursoDAO.findById(anyInt())).willReturn(
                Optional.of(mock(Curso.class)),
                Optional.of(mock(Curso.class))
        );

        var courseBefore = this.cursoService.getById(2);

        //FOC es Fundamentos de organizacion de computadoras
        var foc = new Curso(5, "FOC", 3, "Edi-C");
        cursoService.updateCourse(foc);

        var courseAfter = this.cursoService.getById(5);

        verify(this.cursoDAO, times(1)).update(foc);

        assertThat(courseAfter).isNotSameAs(courseBefore);
    }


    @Test
    public void findByDepartment() throws Exception {
        //TODO: hay que implementarlo
        List<Curso> cursos = Arrays.asList(mock(Curso.class), mock(Curso.class));

        given(cursoDAO.findByDepartment(anyString())).willReturn(cursos);

        var actual = this.cursoService.findByDepartment("Matematica");

        verify(cursoDAO, times(1)).findByDepartment("Matematica");

        assertThat(actual).hasSize(2);
    }

}