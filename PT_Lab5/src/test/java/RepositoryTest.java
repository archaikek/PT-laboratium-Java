import org.example.Mage;
import org.example.MageRepository;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class RepositoryTest {
	@Test
	public void erase_MagePresent_DoesNotThrow() {
		//Arrange:
		Collection<Mage> collection = new HashSet<>();
		collection.add(new Mage("Hikari", 295));
		MageRepository repository = new MageRepository(collection);

		//Act:
		Throwable thrown = catchThrowable(() -> {
			repository.erase("Hikari");
		});

		//Assert:
		assertThat(thrown).doesNotThrowAnyException();
	}

	@Test
	public void erase_MageMissing_Throws() {
		//Arrange:
		MageRepository repository = new MageRepository();

		//Act:
		Throwable thrown = catchThrowable(() -> {
			repository.erase("Hikari");
		});

		//Assert:
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void find_MageFound_OptionalWithContent() {
		//Arrange:
		Collection<Mage> collection = new HashSet<>();
		collection.add(new Mage("Hikari", 295));
		MageRepository repository = new MageRepository(collection);

		//Act:
		Optional<Mage> result = repository.find("Hikari");

		//Assert:
		assertThat(result.get().getName()).isEqualTo("Hikari");
	}

	@Test
	public void find_MageMissing_OptionalEmpty() {
		//Arrange:
		MageRepository repository = new MageRepository();

		//Act:
		Optional<Mage> result = repository.find("Hikari");

		//Assert:
		assertThat(result).isEqualTo(Optional.empty());
	}

	@Test
	public void insert_MageAlreadyPresent_Throws() {
		//Arrange:
		Collection<Mage> collection = new HashSet<>();
		collection.add(new Mage("Hikari", 295));
		MageRepository repository = new MageRepository(collection);

		//Act:
		Throwable thrown = catchThrowable(() -> {
			repository.insert(new Mage("Hikari", 0));
		});

		//Assert:
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void insert_MageNotPresent_DoesNotThrow() {
		//Arrange:
		MageRepository repository = new MageRepository();

		//Act:
		Throwable thrown = catchThrowable(() -> {
			repository.insert(new Mage("Hikari", 0));
		});

		//Assert:
		assertThat(thrown).doesNotThrowAnyException();
	}
}
