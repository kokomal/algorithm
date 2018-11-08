/**
 * @Title: BTreePage.java
 * @Package: yuanjun.chen.advanced.btree
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月6日 下午2:15:13
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.advanced.btree;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BTreePage
 * @Description: 单个页面的pojo
 * @author: 陈元俊
 * @date: 2018年11月6日 下午2:15:13
 */
public class BTreeOnePage {
    int dgr = 0;
    /*
     * --------------------------------------------------------- 
     * pgNo: 100232 
     * isLeaf: false 
     * keys: 12,20,33,44,55 (n) 
     * children: 100333, 100334, 100336, 100337, 100455, 100467 (n+1)
     * ---------------------------------------------------------
     */
    Long pgNo;
    Boolean isLeaf;
    private String rawKeys;
    private String rawChildren;
    private int n;
    List<String> keys;
    List<Long> children;
    
    private Long version;
    private NodePersistStatus status;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public NodePersistStatus getStatus() {
        return status;
    }

    public void setStatus(NodePersistStatus status) {
        this.status = status;
    }

    public Long getPgNo() {
        return pgNo;
    }

    public void setPgNo(Long pgNo) {
        this.pgNo = pgNo;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getRawKeys() {
        return rawKeys;
    }

    public void setRawKeys(String rawKeys) {
        this.rawKeys = rawKeys;
    }

    public String getRawChildren() {
        return rawChildren;
    }

    public void setRawChildren(String rawChildren) {
        this.rawChildren = rawChildren;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

    public void parse() throws Exception {
        String[] ks = rawKeys.split(",");
        this.n = ks.length;
        keys = new ArrayList<>(n);
        for (String k : ks) {
            keys.add(k.trim());
        }
        children = new ArrayList<>(n + 1);
        if (!rawChildren.trim().isEmpty()) {
            String[] cs = rawChildren.split(",");
            for (String c : cs) {
                children.add(Long.parseLong(c.trim()));
            }
        }
    }

    @Override
    public String toString() {
        return "BTreeOnePage [pgNo=" + pgNo + ", isLeaf=" + isLeaf + ", rawKeys=" + rawKeys + ", rawChildren="
                + rawChildren + ", n=" + n + ", keys=" + keys + ", children=" + children + "]";
    }

}
