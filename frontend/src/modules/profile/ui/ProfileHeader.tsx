interface Props {
    profile: {
        name: string;
        about?: string;
    };
    mode: "VIEW" | "EDIT";
}

export function ProfileHeader({ profile, mode }: Props) {
    return (
        <section>
        <h1>{profile.name}</h1>

        {mode === "VIEW" && <p>{profile.about}</p>}

        {mode === "EDIT" && (
            <textarea defaultValue={profile.about} />
        )}
        </section>
    );
}
