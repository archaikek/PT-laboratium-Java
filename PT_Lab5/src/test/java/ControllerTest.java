import org.example.Mage;
import org.example.MageController;
import org.example.MageRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;

public class ControllerTest {
	@Test
	public void erase_MagePresent_Done() {
		//Arrange:
		MageRepository repository = mock(MageRepository.class);
		MageController controller = new MageController(repository);

		//Act:
		String message = controller.erase("Hikari");

		//Assert:
		assertThat(message).isEqualTo("done");
	}

	@Test
	public void erase_MageMissing_NotFound() {
		//Arrange:
		MageRepository repository = mock(MageRepository.class);
		doThrow(new IllegalArgumentException()).when(repository).erase("Hikari");
		MageController controller = new MageController(repository);

		//Act:
		String message = controller.erase("Hikari");

		//Assert:
		assertThat(message).isEqualTo("not found");
	}

	@Test
	public void find_MagePresent_Name() {
		//Arrange:
		MageRepository repository = mock(MageRepository.class);
		when(repository.find("Hikari")).thenReturn(Optional.of(new Mage("Hikari")));
		MageController controller = new MageController(repository);

		//Act:
		String message = controller.find("Hikari");

		//Assert:
		assertThat(message).isEqualTo("Hikari");
	}
	@Test
	public void find_MageMissing_NotFound() {
		//Arrange:
		MageRepository repository = mock(MageRepository.class);
		when(repository.find("Hikari")).thenReturn(Optional.empty());
		MageController controller = new MageController(repository);

		//Act:
		String message = controller.find("Hikari");

		//Assert:
		assertThat(message).isEqualTo("not found");
	}

	@Test
	public void insert_MageNotPresent_Done() {
		//Arrange:
		MageRepository repository = mock(MageRepository.class);
		MageController controller = new MageController(repository);

		//Act:
		String message = controller.insert("Hikari", 295);

		//Assert:
		assertThat(message).isEqualTo("done");
	}

	@Test
	public void insert_MagePresent_BadRequest() {
		//Arrange:
		MageRepository repository = mock(MageRepository.class);
		doThrow(new IllegalArgumentException()).when(repository).insert(new Mage("Hikari"));
		MageController controller = new MageController(repository);

		//Act:
		String message = controller.insert("Hikari", 295);

		//Assert:
		assertThat(message).isEqualTo("bad request");
	}
}
