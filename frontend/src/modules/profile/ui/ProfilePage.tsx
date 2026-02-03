import { ProfileHeader } from "./ProfileHeader";

interface ProfilePageProps {
    profile: {
        id: string;
        userId: string;
        accountType: "PERSONAL" | "COMPANY";
        name: string;
        about?: string;
    };
    mode: "VIEW" | "EDIT";
}

export default function ProfilePage({ profile, mode }: ProfilePageProps) {
    return (
        <div>
        <ProfileHeader profile={profile} mode={mode} />
        </div>
    );
}
