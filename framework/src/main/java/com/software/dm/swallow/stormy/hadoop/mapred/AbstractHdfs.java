package com.software.dm.swallow.stormy.hadoop.mapred;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016
 */
public abstract class AbstractHdfs {
    protected FileSystem hdfs = null;
    protected Configuration conf = null;
    protected URI[] uris;

    /**
     *
     */
    public AbstractHdfs() {
    }

    /**
     * @param conf
     */
    public AbstractHdfs(Configuration conf) {
        this.conf = conf;
    }

    /**
     * @throws IOException
     */
    public URI[] getCacheFiles() throws IOException {
        if (this.uris == null)
            uris = DistributedCache.getCacheFiles(this.conf);
        return uris;
    }

    /**
     * @throws IOException
     */
    public void refreshCacheFiles() throws IOException {
        this.uris = DistributedCache.getCacheFiles(this.conf);
    }

    /**
     * @return
     * @throws IOException
     */
    public FileSystem getHdfs() throws IOException {
        if (hdfs == null) {
            hdfs = FileSystem.get(this.conf);
        }
        return hdfs;
    }

    /**
     * @param path_str
     * @param addDir
     * @return
     * @throws IOException
     */
    public List<FileStatus> getFileStatus(String path_str, boolean addDir) throws IOException {
        return this.getFileStatus(new Path(path_str), addDir);
    }

    /**
     * @param path
     * @param addDir
     * @return
     * @throws IOException
     */
    public List<FileStatus> getFileStatus(Path path, boolean addDir) throws IOException {
        List<FileStatus> listFile = new ArrayList<FileStatus>();
        this.getFs(path, addDir, listFile);
        return listFile;
    }

    /**
     * @param p
     * @param addDir
     * @param listFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void getFs(Path p, boolean addDir, List<FileStatus> listFile) throws FileNotFoundException, IOException {
        FileStatus[] fs = getHdfs().listStatus(p);
        for (FileStatus file : fs) {
            if (file.isFile()) {
                listFile.add(file);
            } else if (file.isDirectory() && addDir) {
                this.getFs(file.getPath(), addDir, listFile);
            }
        }
    }

    /**
     * @param path
     * @param addDir
     * @return
     * @throws IOException
     */
    public Set<InputStream> getInputStreams(String path, boolean addDir) throws IOException {
        List<FileStatus> listFile = this.getFileStatus(path, addDir);
        Set<InputStream> set = new HashSet<InputStream>();
        for (FileStatus fileStatus : listFile) {
            set.add(this.getHdfs().open(fileStatus.getPath()));
        }
        return set;
    }

    /**
     * @param path
     * @return
     * @throws IOException
     */
    public Set<InputStream> getInputStreams(String path) throws IOException {
        return this.getInputStreams(path, false);
    }

    public Configuration getConf() {
        return conf;
    }

    /**
     * @param conf
     */
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    /**
     * @param path
     * @param fileName
     * @return
     * @throws IOException
     */
    public InputStream getInputStream(String path, String fileName) throws IOException {
        InputStream inStream = null;
        Path p = new Path(path + File.separator + fileName);
        if (this.getHdfs().exists(p)) {
            inStream = this.getHdfs().open(p, 1024);
        } else {
            throw new IOException(fileName + " is not exists ...");
        }
        return inStream;
    }

    /**
     * @param path
     * @param fileName
     * @return
     * @throws IOException
     */
    public InputStream getInputStream(Path pathName) throws IOException {
        InputStream inStream = null;
        if (this.getHdfs().exists(pathName)) {
            inStream = this.getHdfs().open(pathName, 1024);
        } else {
            throw new IOException(pathName + " is not exists ...");
        }
        return inStream;
    }

    /**
     * @param symlinkName
     * @return
     * @throws IOException
     */
    public InputStream getInputStreamBySymlinkName(String symlinkName) throws IOException {
        InputStream inStream = null;
        if (DistributedCache.getSymlink(this.conf)) {
            inStream = new FileInputStream(symlinkName);
        } else {
            throw new IOException("mapred.create.symlink is set false ...");
        }
        return inStream;
    }

    /**
     * @param fileName
     * @return
     * @throws IOException
     */
    public Path getCacheFilePath(final String fileName) throws IOException {
        Path re_p = null;
        if (null == uris)
            this.getCacheFiles();
        for (URI uri : uris) {
            Path p = new Path(uri);
            if (p.getName().equals(fileName)) {
                re_p = p;
                break;
            }
        }
        if (null == re_p) {
            throw new IOException(fileName + " is not exists ...");
        }
        return re_p;
    }

    private final Set<String> exitsObj = new HashSet<String>();

    /**
     * @param path
     * @param fileName
     * @throws IOException
     */
    public void addCacheFile(String path, String fileName) throws IOException {
        if (exitsObj.contains(fileName)) {
            return;
        }
        exitsObj.add(fileName);
        Path p = new Path(path + File.separator + fileName);
        if (!this.getHdfs().exists(p)) {
            throw new IOException(fileName + " is not exists ...");
        }
        if (DistributedCache.getSymlink(this.conf)) {
            try {
                DistributedCache.addCacheFile(new URI(p.toString() + "#" + fileName), this.conf);
            } catch (URISyntaxException e) {
                throw new IOException(fileName + " is not exists ...");
            }
        } else {
            DistributedCache.addCacheFile(p.toUri(), this.conf);
        }
    }

    /**
     * @param fileName
     * @return
     * @throws IOException
     */
    public InputStream getInputStream(String fileName) throws IOException {
        InputStream inStream = null;
        if (DistributedCache.getSymlink(this.conf)) {
            inStream = this.getInputStreamBySymlinkName(fileName);
        } else {
            inStream = this.getInputStream(getCacheFilePath(fileName));
        }
        return inStream;
    }
}
