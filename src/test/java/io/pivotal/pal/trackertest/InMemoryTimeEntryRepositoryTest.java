package io.pivotal.pal.trackertest;

import io.pivotal.pal.tracker.InMemoryTimeEntryRepository;
import io.pivotal.pal.tracker.TimeEntry;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryTimeEntryRepositoryTest {
    @Test
    public void create() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry createdTimeEntry = repo.create(new TimeEntry(123, 456, "today", 8));

        TimeEntry expected = new TimeEntry(1L, 123, 456, "today", 8);
        assertThat(createdTimeEntry).isEqualTo(expected);

        TimeEntry readEntry = repo.get(createdTimeEntry.getId());
        assertThat(readEntry).isEqualTo(expected);
    }

    @Test
    public void get() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        repo.create(new TimeEntry(123, 456, "today", 8));

        TimeEntry expected = new TimeEntry(1L, 123, 456, "today", 8);
        TimeEntry readEntry = repo.get(1L);
        assertThat(readEntry).isEqualTo(expected);
    }

    @Test
    public void list() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        repo.create(new TimeEntry(123, 456, "today", 8));
        repo.create(new TimeEntry(789, 654, "yesterday", 4));

        List<TimeEntry> expected = asList(
            new TimeEntry(1L, 123, 456, "today", 8),
            new TimeEntry(2L, 789, 654, "yesterday", 4)
        );
        assertThat(repo.list()).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry created = repo.create(new TimeEntry(123, 456, "today", 8));

        TimeEntry updatedEntry = repo.update(
            created.getId(),
            new TimeEntry(321, 654, "tomorrow", 5));

        TimeEntry expected = new TimeEntry(created.getId(), 321, 654, "tomorrow", 5);
        assertThat(updatedEntry).isEqualTo(expected);
        assertThat(repo.get(created.getId())).isEqualTo(expected);
    }

    @Test
    public void delete() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry created = repo.create(new TimeEntry(123, 456, "today", 8));

        repo.delete(created.getId());
        assertThat(repo.list()).isEmpty();
    }

}
