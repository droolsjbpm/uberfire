package org.uberfire.java.nio.fs.jgit;

import java.io.File;

import org.eclipse.jgit.api.ListBranchCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.commons.config.ConfigProperties;

import static org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME;

public class JGitFileSystemProviderConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(JGitFileSystemProviderConfiguration.class);

    public static final String GIT_ENV_KEY_DEFAULT_REMOTE_NAME = DEFAULT_REMOTE_NAME;

    public static final String GIT_DAEMON_ENABLED = "org.uberfire.nio.git.daemon.enabled";
    public static final String GIT_SSH_ENABLED = "org.uberfire.nio.git.ssh.enabled";
    public static final String GIT_NIO_DIR = "org.uberfire.nio.git.dir";
    public static final String GIT_NIO_DIR_NAME = "org.uberfire.nio.git.dirname";

    /**
     * Specifies the list mode for the repository parent directory. Must match one of the enum constants defined in
     * {@link ListBranchCommand.ListMode}.
     */
    public static final String GIT_ENV_KEY_DEST_PATH = "out-dir";
    public static final String GIT_ENV_KEY_USER_NAME = "username";
    public static final String GIT_ENV_KEY_PASSWORD = "password";
    public static final String GIT_ENV_KEY_INIT = "init";

    public static final String SCHEME = "git";
    public static final int SCHEME_SIZE = (SCHEME + "://").length();
    public static final int DEFAULT_SCHEME_SIZE = ("default://").length();

    public static final String REPOSITORIES_CONTAINER_DIR = ".niogit";
    public static final String SSH_FILE_CERT_CONTAINER_DIR = ".security";

    public static final String DEFAULT_HOST_NAME = "localhost";
    public static final String DEFAULT_HOST_ADDR = "127.0.0.1";
    public static final String DAEMON_DEFAULT_ENABLED = "true";
    public static final String DAEMON_DEFAULT_PORT = "9418";
    public static final String SSH_DEFAULT_ENABLED = "true";
    public static final String SSH_DEFAULT_PORT = "8001";
    public static final String SSH_IDLE_TIMEOUT = "10000";
    public static final String SSH_ALGORITHM = "DSA";
    public static final String SSH_CERT_PASSPHRASE = "";
    public static final String DEFAULT_COMMIT_LIMIT_TO_GC = "20";
    public static final String JGIT_FILE_SYSTEM_INSTANCES_CACHE = "20";
    public static final String GIT_ENV_KEY_MIGRATE_FROM = "migrate-from";

    private int commitLimit;
    private boolean daemonEnabled;
    private int daemonPort;
    private String daemonHostAddr;
    private String daemonHostName;

    private boolean sshEnabled;
    private int sshPort;
    private String sshHostAddr;
    private String sshHostName;
    private File sshFileCertDir;
    private String sshAlgorithm;
    private String sshPassphrase;
    private String sshIdleTimeout;

    private File gitReposParentDir;

    private File hookDir;

    boolean enableKetch = false;
    private String httpProxyUser;
    private String httpProxyPassword;
    private String httpsProxyUser;
    private String httpsProxyPassword;
    private int jgitFileSystemsInstancesCache;

    public void load(ConfigProperties systemConfig) {
        LOG.debug("Configuring from properties:");

        final String currentDirectory = System.getProperty("user.dir");

        final ConfigProperties.ConfigProperty enableKetchProp = systemConfig.get("org.uberfire.nio.git.ketch",
                                                                                 "false");

        final ConfigProperties.ConfigProperty hookDirProp = systemConfig.get("org.uberfire.nio.git.hooks",
                                                                             null);
        final ConfigProperties.ConfigProperty bareReposDirProp = systemConfig.get(GIT_NIO_DIR,
                                                                                  currentDirectory);
        final ConfigProperties.ConfigProperty reposDirNameProp = systemConfig.get(GIT_NIO_DIR_NAME,
                                                                                  REPOSITORIES_CONTAINER_DIR);
        final ConfigProperties.ConfigProperty enabledProp = systemConfig.get(GIT_DAEMON_ENABLED,
                                                                             DAEMON_DEFAULT_ENABLED);
        final ConfigProperties.ConfigProperty hostProp = systemConfig.get("org.uberfire.nio.git.daemon.host",
                                                                          DEFAULT_HOST_ADDR);
        final ConfigProperties.ConfigProperty hostNameProp = systemConfig.get("org.uberfire.nio.git.daemon.hostname",
                                                                              hostProp.isDefault() ? DEFAULT_HOST_NAME : hostProp.getValue());
        final ConfigProperties.ConfigProperty portProp = systemConfig.get("org.uberfire.nio.git.daemon.port",
                                                                          DAEMON_DEFAULT_PORT);
        final ConfigProperties.ConfigProperty sshEnabledProp = systemConfig.get(GIT_SSH_ENABLED,
                                                                                SSH_DEFAULT_ENABLED);
        final ConfigProperties.ConfigProperty sshHostProp = systemConfig.get("org.uberfire.nio.git.ssh.host",
                                                                             DEFAULT_HOST_ADDR);
        final ConfigProperties.ConfigProperty sshHostNameProp = systemConfig.get("org.uberfire.nio.git.ssh.hostname",
                                                                                 sshHostProp.isDefault() ? DEFAULT_HOST_NAME : sshHostProp.getValue());
        final ConfigProperties.ConfigProperty sshPortProp = systemConfig.get("org.uberfire.nio.git.ssh.port",
                                                                             SSH_DEFAULT_PORT);
        final ConfigProperties.ConfigProperty sshCertDirProp = systemConfig.get("org.uberfire.nio.git.ssh.cert.dir",
                                                                                currentDirectory);
        final ConfigProperties.ConfigProperty sshIdleTimeoutProp = systemConfig.get("org.uberfire.nio.git.ssh.idle.timeout",
                                                                                    SSH_IDLE_TIMEOUT);
        final ConfigProperties.ConfigProperty sshAlgorithmProp = systemConfig.get("org.uberfire.nio.git.ssh.algorithm",
                                                                                  SSH_ALGORITHM);
        final ConfigProperties.ConfigProperty sshPassphraseProp = systemConfig.get("org.uberfire.nio.git.ssh.passphrase",
                                                                                   SSH_CERT_PASSPHRASE);
        final ConfigProperties.ConfigProperty commitLimitProp = systemConfig.get("org.uberfire.nio.git.gc.limit",
                                                                                 DEFAULT_COMMIT_LIMIT_TO_GC);

        final ConfigProperties.ConfigProperty httpProxyUserProp = systemConfig.get("http.proxyUser",
                                                                                   null);
        final ConfigProperties.ConfigProperty httpProxyPasswordProp = systemConfig.get("http.proxyPassword",
                                                                                       null);
        final ConfigProperties.ConfigProperty httpsProxyUserProp = systemConfig.get("https.proxyUser",
                                                                                    null);
        final ConfigProperties.ConfigProperty httpsProxyPasswordProp = systemConfig.get("https.proxyPassword",
                                                                                        null);

        final ConfigProperties.ConfigProperty jgitFileSystemsInstancesCacheProp = systemConfig.get("org.uberfire.nio.jgit.cache.instances",
                                                                                                   JGIT_FILE_SYSTEM_INSTANCES_CACHE);

        httpProxyUser = httpProxyUserProp.getValue();
        httpProxyPassword = httpProxyPasswordProp.getValue();
        httpsProxyUser = httpsProxyUserProp.getValue();
        httpsProxyPassword = httpsProxyPasswordProp.getValue();

        if (LOG.isDebugEnabled()) {
            LOG.debug(systemConfig.getConfigurationSummary("Summary of JGit configuration:"));
        }

        if (enableKetchProp != null && enableKetchProp.getValue() != null) {
            enableKetch = enableKetchProp.getBooleanValue();
        }

        if (hookDirProp != null && hookDirProp.getValue() != null) {
            hookDir = new File(hookDirProp.getValue());
            if (!hookDir.exists()) {
                hookDir = null;
            }
        }

        gitReposParentDir = new File(bareReposDirProp.getValue(),
                                     reposDirNameProp.getValue());
        commitLimit = commitLimitProp.getIntValue();

        jgitFileSystemsInstancesCache = jgitFileSystemsInstancesCacheProp.getIntValue();

        if (jgitFileSystemsInstancesCache < 1) {
            jgitFileSystemsInstancesCache = Integer.valueOf(JGIT_FILE_SYSTEM_INSTANCES_CACHE);
        }

        daemonEnabled = enabledProp.getBooleanValue();
        if (daemonEnabled) {
            daemonPort = portProp.getIntValue();
            daemonHostAddr = hostProp.getValue();
            daemonHostName = hostNameProp.getValue();
        }

        sshEnabled = sshEnabledProp.getBooleanValue();
        if (sshEnabled) {
            sshPort = sshPortProp.getIntValue();
            sshHostAddr = sshHostProp.getValue();
            sshHostName = sshHostNameProp.getValue();
            sshFileCertDir = new File(sshCertDirProp.getValue(),
                                      SSH_FILE_CERT_CONTAINER_DIR);
            sshAlgorithm = sshAlgorithmProp.getValue();
            sshIdleTimeout = sshIdleTimeoutProp.getValue();
            try {
                Integer.valueOf(sshIdleTimeout);
            } catch (final NumberFormatException exception) {
                LOG.error("SSH Idle Timeout value is not a valid integer - Parameter is ignored, now using default value.");
                sshIdleTimeout = SSH_IDLE_TIMEOUT;
            }
        }
        sshPassphrase = sshPassphraseProp.getValue();
    }

    public boolean httpProxyIsDefined() {
        return (httpProxyUser != null &&
                httpProxyPassword != null) ||
                (httpsProxyUser != null &&
                        httpsProxyPassword != null);
    }

    public int getCommitLimit() {
        return commitLimit;
    }

    public boolean isDaemonEnabled() {
        return daemonEnabled;
    }

    public int getDaemonPort() {
        return daemonPort;
    }

    public String getDaemonHostAddr() {
        return daemonHostAddr;
    }

    public String getDaemonHostName() {
        return daemonHostName;
    }

    public boolean isSshEnabled() {
        return sshEnabled;
    }

    public int getSshPort() {
        return sshPort;
    }

    public String getSshHostAddr() {
        return sshHostAddr;
    }

    public String getSshHostName() {
        return sshHostName;
    }

    public File getSshFileCertDir() {
        return sshFileCertDir;
    }

    public String getSshAlgorithm() {
        return sshAlgorithm;
    }

    public String getSshPassphrase() {
        return sshPassphrase;
    }

    public String getSshIdleTimeout() {
        return sshIdleTimeout;
    }

    public File getGitReposParentDir() {
        return gitReposParentDir;
    }

    public File getHookDir() {
        return hookDir;
    }

    public boolean isEnableKetch() {
        return enableKetch;
    }

    public String getHttpProxyUser() {
        return httpProxyUser;
    }

    public String getHttpProxyPassword() {
        return httpProxyPassword;
    }

    public String getHttpsProxyUser() {
        return httpsProxyUser;
    }

    public String getHttpsProxyPassword() {
        return httpsProxyPassword;
    }

    public int getJgitFileSystemsInstancesCache() {
        return jgitFileSystemsInstancesCache;
    }
}
