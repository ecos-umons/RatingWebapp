package uni.umons.ratingwebapp.domain;

import uni.umons.ratingwebapp.domain.dto.*;

import javax.activation.CommandMap;
import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EntityMappers {

    public static GitUserDto GitUsertoGitUserDto(GitUser gitUser)
    {
        if(gitUser == null)
        {
            return null;
        }
        GitUserDto gitUserDto = new GitUserDto();
        gitUserDto.setGitUserId(gitUser.getGitUserId());
        gitUserDto.setName(gitUser.getName());
        gitUserDto.setRepository(gitUser.getRepository());
        gitUserDto.setComments(EntityMappers.CommentsToCommentsDto(gitUser.getComments()));
        gitUserDto.setRateDtos(EntityMappers.RatesToRatesDto(gitUser.getRates()));

        return gitUserDto;
    }

    public static List<GitUserDto> GitusersToGitusersDto(List<GitUser> gusers)
    {
        if (gusers.size() == 0)
        {
            return null;
        }
        List<GitUserDto> gitUserDtos = new ArrayList<>();
        for(GitUser user : gusers)
        {
            gitUserDtos.add(EntityMappers.GitUsertoGitUserDto(user));
        }
        return gitUserDtos;
    }

    public static List<RatedGitUserDto> GitusersToRatedGitUserDto(List<GitUser> gusers)
    {
        if (gusers.size() == 0)
        {
            return null;
        }
        List<RatedGitUserDto> ratedGitUserDtos = new ArrayList<>();
        for(GitUser user : gusers)
        {
            RatedGitUserDto ratedGitUserDto = new RatedGitUserDto();
            ratedGitUserDto.setId(user.getGitUserId());
            ratedGitUserDto.setName(user.getName());
            ratedGitUserDto.setRepository(user.getRepository());

            if(user.getRates().get(0).getRate() != null)
                ratedGitUserDto.setRate1(user.getRates().get(0).getRate());
            if(user.getRates().get(0).getRateDifficulty()!=null)
                ratedGitUserDto.setRateDifficulty1(user.getRates().get(0).getRateDifficulty());
            if(user.getRates().get(0).getDescription()!= null)
                ratedGitUserDto.setRateDescription1(user.getRates().get(0).getDescription());
            if(user.getRates().get(0).getRater().getUsername() != null)
                ratedGitUserDto.setRater1(user.getRates().get(0).getRater().getUsername());
            if(user.getRates().size()>1) {
                if(user.getRates().get(1).getRate() != null)
                    ratedGitUserDto.setRate2(user.getRates().get(1).getRate());
                if(user.getRates().get(1).getRateDifficulty()!=null)
                    ratedGitUserDto.setRateDifficulty2(user.getRates().get(1).getRateDifficulty());
                if(user.getRates().get(1).getDescription()!= null)
                    ratedGitUserDto.setRateDescription2(user.getRates().get(1).getDescription());
                if(user.getRates().get(1).getRater().getUsername() != null)
                    ratedGitUserDto.setRater2(user.getRates().get(1).getRater().getUsername());
            }else{
                ratedGitUserDto.setRate2(-1);
                ratedGitUserDto.setRateDifficulty2(-1);
                ratedGitUserDto.setRateDescription2("");
                ratedGitUserDto.setRater2("");
            }
            ratedGitUserDtos.add(ratedGitUserDto);
        }
        return ratedGitUserDtos;
    }

    public static CommentDto CommentToCommentDto(Comment comment)
    {
        if(comment == null)
        {
            return null;
        }
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthor(comment.getGitUser().getName());
        commentDto.setId(comment.getId());
        commentDto.setIssueType(comment.getIssueType());
        try {
            commentDto.setCreatedAt(comment.getCreatedAt());
        }catch(Exception e) {
            commentDto.setCreatedAt(null);
        }
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    public static List<CommentDto> CommentsToCommentsDto(List<Comment> comments)
    {
        if (comments.size() == 0)
        {
            return null;
        }
        List<CommentDto> commentDtos = new ArrayList<>();
        List<CommentDto> emptyComments = new ArrayList<>();
        for(Comment comment : comments)
        {
            if( comment.getBody().trim().isEmpty())
            {
                emptyComments.add(EntityMappers.CommentToCommentDto(comment));
            }else {
                commentDtos.add(EntityMappers.CommentToCommentDto(comment));
            }
        }
        commentDtos.addAll(emptyComments);
        return commentDtos;
    }

    public static RateDto RateToRateDto(Rate rate)
    {
        if(rate == null)
        {
            return null;
        }
        if(rate.getRate() == 0)
        {
            return null;
        }
        RateDto rateDto = new RateDto();
        rateDto.setRateId(rate.getRateId());
        rateDto.setRatedAt(rate.getRatedAt());
        rateDto.setRate(rate.getRate());
        rateDto.setGitUserName(rate.getGitUser().getName());
        rateDto.setGitUserRepo(rate.getGitUser().getRepository());
        rateDto.setDescription(rate.getDescription());
        rateDto.setRaterName(rate.getRater().getUsername());
        rateDto.setRateDifficulty(rate.getRateDifficulty());
        rateDto.setGitUser(rate.getGitUser().getGitUserId());
        return rateDto;
    }

    public static List<RateDto> RatesToRatesDto(List<Rate> rates)
    {
        if (rates.size() == 0)
        {
            return null;
        }
        List<RateDto> rateDtos = new ArrayList<>();
        for(Rate rate : rates)
        {
            if(EntityMappers.RateToRateDto(rate) != null)
                rateDtos.add(EntityMappers.RateToRateDto(rate));
        }
        return rateDtos;
    }

    public static UserDto UsertoUserDto(User user)
    {
        if(user == null)
        {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setUserId(user.getUserId());
        userDto.setUserRoles(EntityMappers.RolestoRolesDto(user.getUserRoles()));
        return userDto;
    }

    public static UserRoleDto RoletoRoleDto(UserRole role)
    {
        if(role==null)
        {
            return null;
        }
        UserRoleDto roleDto = new UserRoleDto();

        roleDto.setUserid(role.getUserid());
        roleDto.setUserRoleId(role.getUserRoleId());
        roleDto.setUserRoleName(role.getUserRoleName());
        return roleDto;
    }

    public static List<UserRoleDto> RolestoRolesDto(List<UserRole> roles)
    {
        if (roles.size() == 0)
        {
            return null;
        }
        List<UserRoleDto> roleDtos = new ArrayList<>();
        for(UserRole role : roles)
        {
            roleDtos.add(EntityMappers.RoletoRoleDto(role));
        }
        return roleDtos;
    }
}
