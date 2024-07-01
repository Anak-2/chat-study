package study.chat.global.api_response;

import java.util.List;

public class CollectionApiResponse<T> {

    private List<T> data;

    private CollectionApiResponse(final List<T> data) {
        this.data = data;
    }

    public static <T> CollectionApiResponse<T> from(final List<T> data) {
        return new CollectionApiResponse<>(data);
    }
}
