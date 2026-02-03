import { notFound } from "next/navigation";

interface PageProps {
    params: Promise<{
        profileId: string;
    }>;
}

export default async function ProfilePageRoute({ params }: PageProps) {
    const { profileId } = await params;

    if (!profileId) {
        notFound();
    }

    return (
        <div style={{ padding: "2rem", color: "black" }}>
            <h1>Profile Page</h1>
            <p>Profile ID: {profileId}</p>
        </div>
    );
}
