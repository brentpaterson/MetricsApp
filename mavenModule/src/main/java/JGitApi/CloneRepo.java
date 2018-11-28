package JGitApi;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.io.IOException;
/*
*  Programmer : Perry Gill
*  purpose : TO clone a repository
*
*
*  */
public class CloneRepo {




    public void clone(String uri, File file)    {
        File localPath = null;
        try {
            localPath = File.createTempFile("GitRepo","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Git cloneResult = Git.cloneRepository()
                .setURI(uri)
                .setDirectory(file)
                .call();

        } catch (GitAPIException e) {
            e.printStackTrace();
        }


    }

}
