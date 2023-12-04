package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "project_members", schema = "crm", catalog = "")
public class ProjectMembersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "project_members_id")
    private int projectMembersId;
    @Basic
    @Column(name = "project_id")
    private Integer projectId;
    @Basic
    @Column(name = "personal_id")
    private Integer personalId;
    @Basic
    @Column(name = "team_name")
    private String teamName;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "projects_id", insertable = false, updatable = false)
    private ProjectsEntity projectsByProjectId;
    @ManyToOne
    @JoinColumn(name = "personal_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByPersonalId;

    public int getProjectMembersId() {
        return projectMembersId;
    }

    public void setProjectMembersId(int projectMembersId) {
        this.projectMembersId = projectMembersId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectMembersEntity that = (ProjectMembersEntity) o;

        if (projectMembersId != that.projectMembersId) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (personalId != null ? !personalId.equals(that.personalId) : that.personalId != null) return false;
        if (teamName != null ? !teamName.equals(that.teamName) : that.teamName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectMembersId;
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (personalId != null ? personalId.hashCode() : 0);
        result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
        return result;
    }

    public ProjectsEntity getProjectsByProjectId() {
        return projectsByProjectId;
    }

    public void setProjectsByProjectId(ProjectsEntity projectsByProjectId) {
        this.projectsByProjectId = projectsByProjectId;
    }

    public PersonalEntity getPersonalByPersonalId() {
        return personalByPersonalId;
    }

    public void setPersonalByPersonalId(PersonalEntity personalByPersonalId) {
        this.personalByPersonalId = personalByPersonalId;
    }
}
