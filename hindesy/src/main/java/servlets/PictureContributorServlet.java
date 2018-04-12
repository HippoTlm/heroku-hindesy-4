package servlets;

import services.ContributorService;
import services.PartnerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/picturecontributor")
public class PictureContributorServlet extends AbstractGenericServlet {

    private Map<String, String> mimeTypes;

    @Override
    public void init() throws ServletException {
        mimeTypes = new HashMap<>();
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("gif", "image/gif");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {

        Integer Id = Integer.parseInt(req.getParameter("id"));
        Path picturePath = ContributorService.getInstance().getPicturePath(Id);

        String pictureFileName = picturePath.getFileName().toString();
        String pictureFileExtension = pictureFileName.substring(pictureFileName.lastIndexOf(".") + 1);
        String contentType = mimeTypes.get(pictureFileExtension);

        resp.setContentType(contentType);
        Files.copy(picturePath, resp.getOutputStream());


    }
}
