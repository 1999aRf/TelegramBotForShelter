package shelter.bot.botshelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Фото не скачивается")
public class DownloadPhotoException extends RuntimeException {
    public DownloadPhotoException() {
        super("Проблемы с загрузкой фотографии");
    }
}
