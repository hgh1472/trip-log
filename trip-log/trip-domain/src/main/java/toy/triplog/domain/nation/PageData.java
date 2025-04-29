package toy.triplog.domain.nation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageData<T> {

    private List<T> data;

    private int page;

    private int totalPages;

    private long totalElements;

}
